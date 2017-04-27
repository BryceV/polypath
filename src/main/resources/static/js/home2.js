$(function(){
    /*
        DOM Elements
    */
    var signOutButton = $("#signOutButton");
    var userInfo = $("#userInfo");
    var addCourseButton = $(".add-course");
    var courseSearchModal = $("#add-course-modal");
    var autocomplete = $('#autocomplete').autocomplete();
    var courseSearch = $('#courseSearch');
    var courseIdField = $("#courseId");
    var courseNameField = $("#courseName");
    // var flowchartContainer = $("#flowchart-container");
    var flowchartContainer = $("#flow-container");
    var addQuarterButton = $("#addQuarterButton");

    /*
        Data Objects
    */
    var selectedQuarter = {};
    var selectedQuarterId = 0;
    var courses = [];
    var flowcharts = {};
    var quarters = ["Fall 2016", "Winter 2017", "Spring 2017", "Summer 2017"];
    var quarterIndex = 0;

    var currentFlowchartId = 0;
    var lastQuarterId = 0;

    var quartersMap = {};
    var coursesMap = {};

    /*
        Click Listeners
    */
    function setFlowchartListClickhandlers() {
        $('.flowchart-name').click(function (e) {
            var id = $(this)[0].getAttribute('flowchart-id');
            // $('.flowchart-container').html(
            //     '<div class="container-fluid" height="20px">Flowchart</div>'
            // );
            // console.log("id: " + id);

            //Clear flowchart container
            flowchartContainer.empty();
            getFlowchartById(id);
        });
    }

    function getFlowchartById(id) {
        $.ajax({
            type: "GET",
            url: "/flowcharts/" + id,
            contentType: "application/json",
            dataType: "json"
        }).done(function(data) {
            console.log("Loading flowcharts");

            parseEntries(data);
            buildFlowchart();

        }).fail(function () {
            console.log("Error loading flowchart list");
        });
    }

    function setAddCourseClickHandlers() {
        $(".add-course").click(function (e) {
            selectedQuarter = e.currentTarget;
            selectedQuarterId = parseInt(e.currentTarget.attributes.quarter_id.nodeValue);
        });
    }

    signOutButton.click(function () {
        $.ajax({
            type: "GET",
            url: "/signout"
        }).done(function (data, textStatus, jqXHR) {
            console.log("Logged out of PolyPaths");
            $(location).attr('href', '/login'); //redirect to login page
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status);
            if (jqXHR.status == 401) {
                $(location).attr('href', '/login'); //redirect to login page
            }
        });
    });

    addQuarterButton.click(function () {
        // load user
        $.ajax({
            type: "GET",
            url: "/quarters/" + ++lastQuarterId,
            contentType:"application/json",
            dataType: "json"
        }).done(function (data, textStatus, jqXHR) {
            console.log(data);
            var qname = data.term + " " + data.year;
            addQuarterDiv(data.id, qname);
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status);
            if (jqXHR.status == 401) {
                $(location).attr('href', '/login'); //redirect to login page
            }
        });
    });

    /*
        Ajax Calls
    */

    // load user
    $.ajax({
        type: "GET",
        url: "/users/me",
        contentType:"application/json",
        dataType: "json"
    }).done(function (data, textStatus, jqXHR) {
        // console.log(data);
        userInfo.text(data.firstName + " " + data.lastName + " (" + data.roles[0] + ")");
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log(jqXHR.status);
        if (jqXHR.status == 401) {
            $(location).attr('href', '/login'); //redirect to login page
        }
    });

    // load courses
    $.ajax({
        type: "GET",
        url: "/courses"
    }).done(function (data, textStatus, jqXHR) {
        console.log("Retrieved list of courses.");

        courses = formatDataForAutocomplete(data);

        initAutocomplete();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.log("Error: " + jqXHR.status);
    });

    function saveEntry(entry) {
        $.ajax({
            type: "POST",
            url: "/entries",
            contentType:"application/json",
            data: JSON.stringify(entry)
        }).done(function (data, textStatus, jqXHR) {
            console.log("Saved Entry.");
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log("Error saving entry: " + jqXHR.status);
        });
    }

    /*
        Course-related methods
     */

    function parseEntries(data){
        currentFlowchartId = data.id;
        quartersMap = {};
        coursesMap = {};

        var entry;
        var quarter;
        for(var i = 0; i < data.entries.length; i++){
            var entry = data.entries[i];
            var quarter = entry.quarter;
            var course = entry.course;
            var quarterId = quarter.id === undefined ? quarter.toString() : quarter.id.toString();
            if (quartersMap[quarterId] === undefined) {
                lastQuarterId = quarterId;
                quartersMap[quarterId] = quarter;
            }

            if (coursesMap[quarterId] === undefined) {
                coursesMap[quarterId] = [];
            }
            coursesMap[quarterId].push(course);
        }
    }

    function buildFlowchart(){
        // Add quarters
        for(var id in quartersMap) {
            var quarterName = quartersMap[id].term + " " + quartersMap[id].year;
            addQuarterDiv(id ,quarterName);
            var courseArr = coursesMap[id];
            courseArr.forEach(function(c){
                addCourse(id, c);
            });
        }
        setAddCourseClickHandlers();
    }

    function addCourse(id, course) {
        var addCourseDivId = "#addCourse_" + id;
        var divId = "course_" + course.id;

        var courseDiv = $('<div/>', {
            id: divId,
            class: "course"
        });
        courseDiv.text(course.department.prefix + " " + course.number);

        $(addCourseDivId).before(courseDiv);
    }

    function addCourse(id, course) {
        var addCourseDivId = "#addCourse_" + id;
        var divId = "course_" + course.id;

        var courseDiv = $('<div/>', {
            id: divId,
            class: "course"
        });
        courseDiv.text(course.department.prefix + " " + course.number);

        $(addCourseDivId).before(courseDiv);
    }

    function getCourse(c) {
        $.ajax({
            type: "GET",
            url: "/courses/" + c,
            contentType: "application/json",
            dataType: "json"
        }).done(function (data, textStatus, jqXHR) {
            return data;
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status);
            if (jqXHR.status == 401) {
                $(location).attr('href', '/login'); //redirect to login page
            }
        });
    }

    function addQuarterToFlowchart(){

    }

    function addCourseToQuarter(course, quarter){

    }

    function addQuarterDiv(id, name){
        var quarterDivId = "quarter_" + id;
        var addCourseDivId = "addCourse_" + id;

        var quarterDiv = $('<div/>', {
            id: quarterDivId,
            class: "quarter-container"
        });
        quarterDiv.text(name);

        var addCourseDiv = $('<div/>', {
            id: addCourseDivId,
            class: "add-course"
        });
        addCourseDiv.attr('quarter_id', id);
        addCourseDiv.attr('data-toggle','modal');
        addCourseDiv.attr('data-target','#add-course-modal');
        addCourseDiv.text("Add Course");

        quarterDiv.append(addCourseDiv);
        // quarterDiv.append('<div class="quarter-title">' + name + '</div><div class="add-course" data-toggle="modal" data-target="#add-course-modal">Add Course</div>');
        // flowchartContainer.append('<div class="quarter-container"><div class="quarter-title">' + name + '</div><div class="add-course" data-toggle="modal" data-target="#add-course-modal">Add Course</div></div>');
        flowchartContainer.append(quarterDiv);
        setAddCourseClickHandlers();

        return quarterDiv;
    }

    /*
        Course Search
    */
    function initAutocomplete() {
        $('#courseSearch').autocomplete({
            lookup: courses,
            groupBy: 'department',
            onSelect: function (suggestion) {
                var course = suggestion.data.course;
                // courseNameField.val(suggestion.value);
                // courseIdField.val(suggestion.data.course.id);
                courseSearchModal.modal('hide');
                addCourse(selectedQuarterId, course);
                courseSearch.val('');

                var entry = {
                    "flowchart_id": currentFlowchartId,
                    "course_id": course.id,
                    "quarter_id": selectedQuarterId
                };
                saveEntry(entry);
            }
        });
    }

    function formatDataForAutocomplete(data){
        return $.map(data, function(dataItem) {
            return { value: dataItem.department.prefix + " " + dataItem.number + " - " + dataItem.title, data: {department: dataItem.department.prefix, course: dataItem} };
        })
    }

    function loadFlowchartList() {
        $.ajax({
            type: "GET",
            url: "/flowcharts",
            contentType: "application/json",
            dataType: "json"
        }).done(function(data) {
            console.log("Loading flowcharts");
            console.log(data);
            var firstID = data[0].id;

            data.forEach(function (item) {
                flowcharts[item.id] = item;
                $('#flowchartList').append('<button class="flowchart-name btn btn-primary" flowchart-id="' + item.id + '">' + item.name + '</button>');
                $('#flowchartList').append('<br />');
            });

            parseEntries(flowcharts[firstID.toString()]);
            buildFlowchart();
            setFlowchartListClickhandlers();

        }).fail(function () {
            console.log("Error loading flowchart list");
        });
    }


    // load user's flowcharts
    loadFlowchartList();
});