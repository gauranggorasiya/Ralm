package wait.list.manager.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import wait.list.manager.utility.AppConstant;

public class Customer extends RealmObject {

    @PrimaryKey
    private long id;
    private String fullName;
    private String gstNumber;
    private String address;
    private String email;
    private String mobile;
    private String date;
    private String idEtallocate = AppConstant.TABLE;
    private String status= AppConstant.BOOKED;
    private double outStanding;
    private int paidBill;
    private int unpaidBill;

    private boolean isSelect;

   /* public static List<Customer> getListOfCustomer() {

        List<Customer> customerList=new ArrayList<>();
        customerList.add(new Customer(1,"Kamlesh Gorasiya","24SGDSEREFE2342S","A/95- Tulsidarashan Society, Yogi Chowk, Varachha, Surat, Gujarat, 395010","kamlesh.gorasiya@gamil.com","9227535285"));
        customerList.add(new Customer(2,"Gaurang Gorasiya","24SGDSEREFE2342S","A/95- Tulsidarashan Society, Yogi Chowk, Varachha, Surat, Gujarat, 395010","kamlesh.gorasiya@gamil.com","9227535285"));
        customerList.add(new Customer(3,"Jayesh Kalkani","24SGDSEREFE2342S","A/95- Tulsidarashan Society, Yogi Chowk, Varachha, Surat, Gujarat, 395010","kamlesh.gorasiya@gamil.com","9227535285"));
        customerList.add(new Customer(4,"Hardik Mavani","24SGDSEREFE2342S","A/95- Tulsidarashan Society, Yogi Chowk, Varachha, Surat, Gujarat, 395010","kamlesh.gorasiya@gamil.com","9227535285"));
        customerList.add(new Customer(5,"Parth Gorasiya","24SGDSEREFE2342S","A/95- Tulsidarashan Society, Yogi Chowk, Varachha, Surat, Gujarat, 395010","kamlesh.gorasiya@gamil.com","9227535285"));
        customerList.add(new Customer(6,"Ankur Kalkani","24SGDSEREFE2342S","A/95- Tulsidarashan Society, Yogi Chowk, Varachha, Surat, Gujarat, 395010","kamlesh.gorasiya@gamil.com","9227535285"));
        return customerList;
    }*/

    public Customer(long id, String fullName, String gstNumber, String mobile, String date , double outStanding, int paidBill, int unpaidBill) {
        this.id = id;
        this.fullName = fullName;
        this.gstNumber = gstNumber;
        this.mobile = mobile;
        this.date = date;
        // this.email = email;
        // this.mobile = mobile;
        this.outStanding = outStanding;
        this.paidBill = paidBill;
        this.unpaidBill = unpaidBill;
    }
    public Customer(String fullName, String gstNumber, String address, String email, String mobile, double outStanding, int paidBill, int unpaidBill) {

        this.fullName = fullName;
        this.gstNumber = gstNumber;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.outStanding = outStanding;
        this.paidBill = paidBill;
        this.unpaidBill = unpaidBill;
    }

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTable() {
        return idEtallocate;
    }

    public void setTable(String idEtallocate) { this.idEtallocate = idEtallocate; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(double outStanding) {
        this.outStanding = outStanding;
    }

    public int getPaidBill() {
        return paidBill;
    }

    public void setPaidBill(int paidBill) {
        this.paidBill = paidBill;
    }

    public int getUnpaidBill() {
        return unpaidBill;
    }

    public void setUnpaidBill(int unpaidBill) {
        this.unpaidBill = unpaidBill;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {

        return "Customer{" +
                " " + id +' ' +fullName + ' ' + gstNumber + ' ' + idEtallocate + ' ' + date + ' ' + mobile + ' ' + status + ' ' + '}';
    }

}
