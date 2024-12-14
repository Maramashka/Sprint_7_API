package model;

public class CourierDelete {
    int courierId;

    public CourierDelete(int courierId) {
        this.courierId = courierId;
    }

    public CourierDelete() {
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }
}
