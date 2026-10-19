package Entity;

import java.util.Comparator;

/**
 * Sinh vien gom: id, studentName, semester, courseName.
 *
 * @author THAYCACAC
 */
public class Student {

    private String id;
    private String studentName;
    private String semester;
    private String courseName;

    public Student() {
    }

    public Student(String id, String studentName, String semester, String courseName) {
        this.id = id;
        this.studentName = studentName;
        this.semester = semester;
        this.courseName = courseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * In 1 dong thong tin sinh vien (khong in id).
     */
    public void print() {
        System.out.printf("%-15s%-15s%-15s\n", studentName, semester, courseName);
    }

    /**
     * Comparator sap xep sinh vien theo ten tang dan (A -> Z).
     * Dung voi Collections.sort(list, Student.byName).
     */
    public static final Comparator<Student> byName = new Comparator<Student>() {
        @Override
        public int compare(Student s1, Student s2) {
            return s1.getStudentName().compareToIgnoreCase(s2.getStudentName());
        }
    };
}