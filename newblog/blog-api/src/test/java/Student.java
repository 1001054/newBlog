import java.util.ArrayList;
import java.util.List;

class Main{
    public static void main(String[] args) {
    }
}
//class Student {
//    private String name;
//    private Integer ID;
//    private String programmeOfStudy;
//    private String address;
//    private static String universityName;
//
//    //since all the universityName is the same,
//    //this property should be separated from other fields
//    //and this method should be executed before creating the objects
//    public static void setUniversityName(String universityName) {
//        Student.universityName = universityName;
//    }
//
//    public void insert(String name, Integer ID, String programmeOfStudy, String address){
//        this.name = name;
//        this.ID = ID;
//        this.programmeOfStudy = programmeOfStudy;
//        this.address = address;
//    }
//
//    public void print() {
//        System.out.println("Student{" +
//                "name='" + name + '\'' +
//                ", ID=" + ID +
//                ", programmeOfStudy='" + programmeOfStudy + '\'' +
//                ", address='" + address + '\'' +
//                ", universityName='" + universityName + '\'' +
//                '}');
//    }
//    /*
//    according to the requirement, there is no need to create
//    a constructor, when other class use this class to create
//    a object, the system will run the default constructor
//    without parameters
//     */
//}

//class Employee{
//    private String name;
//    private Integer ID;
//    private String address;
//    private String universityName;
//
//    //create two different constructors for different situations
//    public Employee(){}
//    //the constructor with four parameters
//    public Employee(String name, Integer ID, String address, String universityName) {
//        this.name = name;
//        this.ID = ID;
//        this.address = address;
//        this.universityName = universityName;
//    }
//
//    //this method is for inserting the values of fields
//    public void insert(String name, Integer ID, String address, String universityName){
//        this.name = name;
//        this.ID = ID;
//        this.address = address;
//        this.universityName = universityName;
//    }
//
//    //this method is for retrieving by printing on the screen
//    public void retrieve(){
//        System.out.println("Student{" +
//                "name='" + name + '\'' +
//                ", ID=" + ID +
//                ", address='" + address + '\'' +
//                ", universityName='" + universityName + '\'' +
//                '}');
//    }
//
//    //and these methods are for retrieving by returning the values
//    public String getName() {
//        return name;
//    }
//
//    public Integer getID() {
//        return ID;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public String getUniversityName() {
//        return universityName;
//    }
//}
//
//class AcademicStaff extends Employee{
//    private String positionTitle;
//    public static ArrayList<AcademicStaff> list = new ArrayList<>();
//
//    //the constructor with 5 parameters
//    //and the new object will be added into the list
//    public AcademicStaff(String name, Integer ID, String address, String universityName, String positionTitle) {
//        super(name, ID, address, universityName);
//        this.positionTitle = positionTitle;
//        //put this new object into the list
//        list.add(this);
//    }
//
//    //print the particular employee with the parameter ID
//    public static void findByID(Integer ID){
//        for(Employee employee : list){
//            if(employee.getID() == ID){
//                employee.retrieve();
//                return;
//            }
//        }
//    }
//
//    //the override retrieve
//    @Override
//    public void retrieve(){
//        System.out.println("Student{" +
//                "name='" + this.getName() + '\'' +
//                ", ID=" + this.getID() +
//                ", address='" + this.getAddress() + '\'' +
//                ", universityName='" + this.getUniversityName() + '\'' +
//                ", positionTitle='" + this.getPositionTitle() + '\'' +
//                '}');
//    }
//
//    public String getPositionTitle() {
//        return positionTitle;
//    }
//}
//
//class ResearchStaff extends Employee{
//    public static ArrayList<ArrayList<ResearchStaff>> domains = new ArrayList<>();
//    private String domain;
//
//    //when a new object is created, the object will be added
//    //into the static list by its domain
//    public ResearchStaff(String name, Integer ID, String address, String universityName, String domain) {
//        super(name, ID, address, universityName);
//        this.domain = domain;
//        ArrayList<ResearchStaff> list = this.search();
//        if( list == null){
//            list = new ArrayList<>();
//            domains.add(list);
//        }
//        list.add(this);
//    }
//
//    //the results are based on the particular object
//    public ArrayList search(){
//        for(ArrayList<ResearchStaff> list : domains){
//            if(!list.isEmpty() && list.get(0).getDomain().equals(this.domain)){
//                return list;
//            }
//        }
//        return null;
//    }
//
//    public String getDomain() {
//        return domain;
//    }
//
//    @Override
//    public void retrieve() {
//        System.out.println("Student{" +
//                "name='" + this.getName() + '\'' +
//                ", ID=" + this.getID() +
//                ", address='" + this.getAddress() + '\'' +
//                ", universityName='" + this.getUniversityName() + '\'' +
//                ", domain='" + this.getDomain() + '\'' +
//                '}');
//    }
//}
//
//class Student extends ResearchStaff{
//    private String programmeOfStudy;
//    public static ArrayList<Student> students = new ArrayList<>();
//
//    //the constructor with parameters
//    //and add this object into the static students list
//    public Student(String name, Integer ID, String address, String universityName, String domain, String programmeOfStudy) {
//        super(name, ID, address, universityName, domain);
//        this.programmeOfStudy = programmeOfStudy;
//        students.add(this);
//    }
//
//    //return the id list of whose programme of study is computer
//    @Override
//    public ArrayList search(){
//        ArrayList<Integer> IDs = new ArrayList<>();
//        for(Student student : students){
//            if( "computer".equals(student.getProgrammeOfStudy())){
//                IDs.add(student.getID());
//            }
//        }
//        return IDs;
//    }
//
//    public String getProgrammeOfStudy() {
//        return programmeOfStudy;
//    }
//}
//
//class Meeting{
//    private String time;
//    private String location;
//    private String subject;
//
//    //the constructor with four parameters
//    public Meeting(String time, String location, String subject) {
//        this.time = time;
//        this.location = location;
//        this.subject = subject;
//    }
//
//    //to set the time
//    public void setTime(String time) {
//        this.time = time;
//    }
//    //to set the location
//    public void setLocation(String location) {
//        this.location = location;
//    }
//    //to set the subject
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }
//    //to return the subject of the meeting
//    public String getSubject() {
//        return subject;
//    }
//    //to print all information of a meeting
//    public void printDetails(){
//        System.out.println("Meeting in " + location +
//                " at " + time + "; " +
//                "Subject: " + subject + ".");
//    }
//}

//class Shape{
//    double x;
//    double y;
//}
//
//interface Area{
//    double area();
//}
//
//class Rectangle extends Shape implements Area{
//    private double width;
//    private double height;
//
//    //the constructor with two parameters
//    public Rectangle(double width, double height) {
//        this.width = width;
//        this.height = height;
//    }
//
//    @Override
//    public double area(){
//        return width * height;
//    }
//}
//
//class Ellipse extends Shape implements Area{
//    private double major_axis;
//    private double minor_axis;
//
//    //the constructor with two parameters
//    public Ellipse(double major_axis, double minor_axis) {
//        this.major_axis = major_axis;
//        this.minor_axis = minor_axis;
//    }
//
//    @Override
//    public double area(){
//        return 3.14159265 * (major_axis / 2) * (minor_axis / 2);
//    }
//}
//
//class Triangle extends Shape implements Area{
//    private double side1;
//    private double side2;
//    private double angle_between;
//
//    //the constructor with three parameters
//    public Triangle(double side1, double side2, double angle_between) {
//        this.side1 = side1;
//        this.side2 = side2;
//        this.angle_between = angle_between;
//    }
//
//    @Override
//    public double area(){
//        //transfer the angle to radians
//        //transfer the radians to sin
//        return side1 * side2 * Math.sin(Math.toRadians(angle_between)) / 2;
//    }
//}


//class Queue{
//    private ArrayList<Integer> list = new ArrayList<>();
//
//    //insert a new element to its position
//    //compare it with the elements from the head
//    // in the list and until find a bigger element
//    //then put this new element in the front of it
//    public void insert(Integer element){
//        int index = 0;
//        while(list.size() > 0 && list.get(index) <= element ){
//            index++;
//        }
//        list.add(index,element);
//    }
//
//    //find the second element in the list
//    public Integer find2(){
//        if(list.size() > 1){
//            return list.get(1);
//        }else{
//            return null;
//        }
//    }
//
//    //delete the head element(the smallest)
//    public void delete(){
//        if(list.size() > 0)
//        list.remove(0);
//    }
//
//    //check if this queue is empty
//    boolean isEmpty(){
//        return list.isEmpty();
//    }
//
//    //return the string of list
//    @Override
//    public String toString() {
//        return list.toString();
//    }
//}
//
//
//
//class Stack{
//    private ArrayList<Integer> list = new ArrayList();
//
//    //print the elements from the top to the bottom
//    public static void printStack(Stack s){
//        for(int i = s.size() - 1; i >= 0; i--){
//            System.out.print(s.getList().get(i) + " ");
//        }
//        System.out.println();
//    }
//    //reverse the elements of stack to a new stack
//    public static Stack reverseStack(Stack s){
//        Stack res = new Stack();
//        for(int i = s.size() - 1; i >= 0; i--){
//            res.list.add(s.getList().get(i));
//        }
//        return res;
//    }
//    //remove a element from the stack
//    public static Stack removeElement(Stack s, int val){
//        Stack res = new Stack();
//        for(Integer i : s.getList()){
//            if(i != val){
//                res.push(i);
//            }
//        }
//        return res;
//    }
//
//    //return the top element without changing stack
//    public Integer peek(){
//        return list.get(list.size()-1);
//    }
//    //pop the top element in the stack
//    public Integer pop() {
//        return list.remove(list.size()-1);
//    }
//    //push a new element into the stack
//    public void push(Integer element){
//        list.add(element);
//    }
//    //get the size of the stack
//    public int size(){
//        return list.size();
//    }
//    //return the list of this stack
//    public ArrayList<Integer> getList() {
//        return list;
//    }
//}

class LinkedNode{
    private Integer val;
    private LinkedNode next;
    //the constructor with two parameters
    public LinkedNode(Integer val, LinkedNode next) {
        this.val = val;
        this.next = next;
    }
    //print the reversed val
    public static void reverse(LinkedNode head){
        LinkedNode p = head;
        ArrayList<Integer> list = new ArrayList<>();
        //iterate the link and add the val into a list
        while(p != null){
            list.add(p.getVal());
            p = p.getNext();
        }
        //print the elements from the end to the front
        for(int i = list.size()-1; i >= 0; i--){
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }
    //the getters and setters
    public Integer getVal() {
        return val;
    }
    public void setVal(Integer val) {
        this.val = val;
    }
    public LinkedNode getNext() {
        return next;
    }
    public void setNext(LinkedNode next) {
        this.next = next;
    }
}


