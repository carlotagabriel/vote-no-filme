package carlota.components.database.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;

public class DAO<ObjectType> {

    protected EntityManagerFactory emf = null;

    public EntityManager getEM() {
        try {
            return emf.createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Class<ObjectType> typedClass;
    
    protected Class<ObjectType> getTypedClass() {
        return typedClass;
    }

    public DAO(Class<ObjectType> aTypedClass,
            EntityManagerFactory anEmf) {
        
        this.typedClass = aTypedClass;
        this.emf = anEmf;
    }

    public DAO(Class<ObjectType> aTypedClass) {
        this.typedClass = aTypedClass;
        this.emf = DAOManager.getEMF();
    }

    public ObjectType find(int id) {
        
        EntityManager em = this.getEM();
        ObjectType o = null;

        try {
            o = em.find(getTypedClass(), id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            em.close();
        }

        return o;
    }
    
    @SuppressWarnings("unchecked")
    public ObjectType find(String where, Object ...parametros) {

        EntityManager em = this.getEM();
        ObjectType o = null;

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE " + where;
            o = (ObjectType) getQueryForParams(em, query, parametros)
                            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            em.close();
        }

        return o;
    }
    
    public List<ObjectType> listAll(){
        return list("?1",true);
    }

    @SuppressWarnings("unchecked")
    public List<ObjectType> list(String where, Object ...parametros) {

        EntityManager em = this.getEM();
        List<ObjectType> list = null;

        try {
            String query = "SELECT * FROM " + getTableName() + " WHERE " + where;
            list = getQueryForParams(em, query, parametros)
                   .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            em.close();
        }

        return list;
    }

    public void create(ObjectType object) {

        EntityManager em = this.getEM();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(object);
            em.flush();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }finally{
        	em.close();
        }

    }

    public ObjectType update(ObjectType object) {

        EntityManager em = this.getEM();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();    

        try {

            object = em.merge(object);
            em.flush();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
        	em.close();
        }

        return object;
    }

    private String getTableName(){
        Table t = getTypedClass().getAnnotation(
                javax.persistence.Table.class);
        String tableSchema = t.schema();
        String tableName = t.name();
        
        if (tableSchema != null && tableSchema.length() > 0)
            tableName = tableSchema + "." + tableName;
        
        return tableName;
    }
    
    private Query getQueryForParams(EntityManager em, String query, Object... parametros){
        return getQueryForParams(em, query, true, parametros);
    }
    
    private Query getQueryForParams(EntityManager em, String query, 
        boolean useTypedClass, Object... parametros){
        Query q = null;
        if(useTypedClass)
            q = em.createNativeQuery(query, getTypedClass());
        else
            q = em.createNativeQuery(query);
        
        int indParam = 1;
        for(Object param : parametros)
            q.setParameter(indParam++, param);
        
        return q;
    }
    
}