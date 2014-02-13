package carlota.components.database.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOManager {
    
    private static HashMap<String, EntityManagerFactory> ems = new HashMap<String, EntityManagerFactory>();;
    
    public static String defaultEMF = "filme";
    
    public static EntityManagerFactory getEMF(String id)
    {
        if (ems.get(id) == null)
            ems.put(id, Persistence.createEntityManagerFactory(id));
        
        return ems.get(id);
    }
    
    public static EntityManagerFactory getEMF()
    {	
        if (ems.get(defaultEMF) == null)
            ems.put(defaultEMF, Persistence.createEntityManagerFactory(defaultEMF));
        
        return ems.get(defaultEMF);
    }
    
    public static void shutdown()
    {
        Set<String> keys = ems.keySet();
        Iterator<String> it = keys.iterator();
        
        while (it.hasNext())
            ems.get(it.next()).close();
        
        ems = null;
    }

    public static void init(List<String> EMFs) {
        ems.clear();
        for(String EMFName : EMFs)
            getEMF(EMFName);
    }

}
