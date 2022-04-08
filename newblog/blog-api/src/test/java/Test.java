import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.digester.ArrayStack;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Test {
    public static void main(String[] args) {

//        //create two student objects
//        Student student1 = new Student();
//        Student student2 = new Student();
//        //this method is shared by all Student objects
//        Student.setUniversityName("Newcastle University");
//        //insert the fields to this object
//        student1.insert("Jack", 1234, "CS", "SunStreet");
//        student2.insert("Jones", 1235, "CS", "MoonStreet");
//        //print the two objects
//        student1.print();
//        student2.print();


//        //create two employee objects and retrieve
//        Employee employee = new Employee("Jack",123,"SunRoad","Newcastle University");
//        employee.retrieve();
//        Employee employee1 = new Employee();
//        employee1.insert("Jones", 124,"MoonRoad","Newcastle University");
//        employee1.retrieve();
//        //print the got fields
//        System.out.println(employee.getID());
//        System.out.println(employee.getName());
//        System.out.println(employee.getAddress());
//        System.out.println(employee.getUniversityName());
//

//        //create two staff objects
//        AcademicStaff staff = new AcademicStaff("Jack",123,"SunRaod","Newcastle University", "Engineer");
//        AcademicStaff staff1 = new AcademicStaff("Jones", 124,"MoonRoad", "Newcastle University","Boss");
//        //find the staff whose id is 123
//        AcademicStaff.findByID(123);


//        //create three different objects
//        ResearchStaff staff = new ResearchStaff("Jack",123,"SunRoad", "Newcastle University", "Java");
//        ResearchStaff staff1 = new ResearchStaff("Jones",124, "MoodRoad","Newcastle University","C++");
//        ResearchStaff staff2 = new ResearchStaff("Tom", 125, "TreeRoad", "Newcastle University","Java");
//        //get all the researchers who have the same domain with staff ( Jack )
//        ArrayList<ResearchStaff> java = staff.search();
//        for(ResearchStaff r : java){
//            r.retrieve();
//        }

//        //create four students
//        Student student = new Student("Jack",123,"SunRoad","Newcastle University","Java","computer");
//        Student student1 = new Student("Jones",124,"MoonRoad","Newcastle University","Java","computer");
//        Student student2 = new Student("Tom",125,"TreeRoad","Newcastle University","machine","driving");
//        Student student3 = new Student("Jerry",126,"BlueRoad","Newcastle University","Java","computer");
//        //print the computer student
//        System.out.println(student.search());

//        //create a new meeting
//        Meeting meeting = new Meeting("12:30", "room 205", "Examiner's meeting");
//        //print the details of this meeting
//        meeting.printDetails();
//        //set time of the meeting
//        meeting.setTime("13:00");
//        //set the location
//        meeting.setLocation("room 210");
//        //set the subject
//        meeting.setSubject("Lecture");
//        //get the subject
//        System.out.println(meeting.getSubject());
//        //print the changed meeting
//        meeting.printDetails();

//        //create a rectangle
//        Rectangle rectangle = new Rectangle(1,2);
//        //print the area of this rectangle
//        System.out.println(rectangle.area());
//        //create a ellipse
//        Ellipse ellipse = new Ellipse(1,2);
//        //print the area of the ellipse
//        System.out.println(ellipse.area());
//        //create a triangle
//        Triangle triangle = new Triangle(1,1,90);
//        //print the area of the triangle
//        System.out.println(triangle.area());

//        //create a new queue object
//        Queue queue = new Queue();
//        //print the boolean if this queue is empty
//        System.out.println(queue.isEmpty());
//        //insert elements into the queue
//        queue.insert(7);
//        queue.insert(4);
//        queue.insert(1);
//        queue.insert(3);
//        //print the queue in order
//        System.out.println(queue.toString());
//        //print the second element in the queue
//        System.out.println(queue.find2());
//        //delete the front element in the queue
//        queue.delete();
//        //print the queue
//        System.out.println(queue.toString());

//        //create a stack object
//        Stack stack = new Stack();
//        //add three integers
//        stack.push(1);
//        stack.push(2);
//        stack.push(3);
//        //print the elements from top to bottom
//        Stack.printStack(stack);
//        //reverse the order of the elements
//        //name it as stack2
//        Stack stack2 = Stack.reverseStack(stack);
//        //print the unchanged stack
//        Stack.printStack(stack);
//        //print the reversed stack
//        Stack.printStack(stack2);
//        //remove 2 from the stack
//        Stack stack3 = Stack.removeElement(stack,2);
//        //print the unchanged stack
//        Stack.printStack(stack);
//        //print the removed stack
//        Stack.printStack(stack3);

//        //create three nodes and link them together
//        LinkedNode node1 = new LinkedNode(1,null);
//        LinkedNode node2 = new LinkedNode(2,node1);
//        LinkedNode node3 = new LinkedNode(3,node2);
//        //iterate the nodes in order
//        LinkedNode p = node3;
//        while(p != null){
//            System.out.print(p.getVal() + " ");
//            p = p.getNext();
//        }
//        System.out.println();
//        //print the reversed order
//        LinkedNode.reverse(node3);

    }
}
