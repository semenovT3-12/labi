//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package lab3;

public class Model extends Main {
    private final Integer id;
    private final String nameSight;
    private final double latitude;
    private final double longitude;
    private final String region;
    private final String picture;

    public Model(Integer id, String nameSight, double latitude, double longitude, String region, String picture) {
        this.id = id;
        this.nameSight = nameSight;
        this.latitude = latitude;
        this.longitude = longitude;
        this.region = region;
        this.picture = picture;
    }

    public Integer getId() {
        return this.id;
    }

    public String getNameSight() {
        return this.nameSight;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getRegion() {
        return this.region;
    }

    public String getPicture() {
        return this.picture;
    }
}
