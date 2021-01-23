package rs.ac.uns.ftn.education.service.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentCreatorFactory {

    @Autowired
    private List<DocumentCreator> services;

    private static final Map<DocumentCreatorType, DocumentCreator> myServiceCache = new HashMap<>();

    @PostConstruct
    public void initMyServiceCache() {
        for(DocumentCreator service : services) {
            myServiceCache.put(service.getType(), service);
        }
    }

    public static DocumentCreator getCreator(DocumentCreatorType type) {
      DocumentCreator service = myServiceCache.get(type);

      if (service == null) {
        throw new RuntimeException("Unknown service type: " + type);
      }

      return service;
    }
}
