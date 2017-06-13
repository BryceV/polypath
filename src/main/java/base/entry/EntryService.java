package base.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;

    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();
        entryRepository.findAll().forEach(entries::add);
        return entries;
    }

    public Entry getEntry(Long id) {
        return entryRepository.findOne(id);
    }

    public void addEntry(Entry entry)
    {
        entryRepository.save(entry);
    }

    public void updateEntry(Long id, Entry entry)
    {
      entry.setId(id);
      entryRepository.save(entry);
    }

    public void deleteEntry(Long id)
    {
        entryRepository.delete(id);
    }

    public void removeAllEntries()
    {
        entryRepository.deleteAll();
    }

}
