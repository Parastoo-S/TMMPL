package segproject.tmmpl;

/**
 * Created by Lori on 2017-12-02.
 */

public class Item {
    private String _itemId;
    private String _itemName;

    public Item(){

    }

    public Item(String itemName){
        this._itemName = itemName;
    }

    public Item(String id, String itemName){
        this._itemId = id;
        this._itemName = itemName;
    }

    public String getItemName(){
        return _itemName;
    }

    public void setItemName(String newItemName){
        _itemName = newItemName;
    }

    public String getItemId(){
        return _itemId;
    }

    public void setItemId(String newItemId){
        _itemId = newItemId;
    }
}
