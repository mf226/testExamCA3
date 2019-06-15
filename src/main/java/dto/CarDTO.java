package dto;

/**
 *
 * @author Fen
 */
public class CarDTO {
    private String week;
    private String comp;
    private String addr;
    private String type;
    private String make;
    private String price;

    public CarDTO() {
    }

    public CarDTO(String week, String comp, String addr, String type, String make, String price) {
        this.week = week;
        this.comp = comp;
        this.addr = addr;
        this.type = type;
        this.make = make;
        this.price = price;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    
}
