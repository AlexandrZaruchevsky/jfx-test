package za.jfx.servicies;

import java.util.List;

public interface CrudService<T> {

    T add(T t);
    void addAll(List<T> tList);
    T update(T t);
    void delete(Long id);
    T findById(Long id);
    List<T> findAll();

}
