/**
 * https://github.com/auzll/zTool
 *
 * since 2013-1-19 上午10:05:24
 */
package z.tool.entity.interfaces;

import java.util.List;

/**
 * @author auzll
 */
public interface IParent<E> {
    IParent<E> getParent();
    
    IParent<E> addChild(E e);
    
    long getId();
    
    long getParentId();
    
    List<? extends IParent<E>> getChildren();
}
