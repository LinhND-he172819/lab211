package Manager;

import Entity.Report;
import Entity.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Xu ly cac chuc nang: Create, Find and Sort, Update/Delete, Report.
 *
 * @author THAYCACAC
 */
public class Manager {

    //Hien thi menu
    public static void menu() {
        System.out.println("WELCOME TO STUDENT MANAGEMENT");
        System.out.println("1. Create");
        System.out.println("2. Find and Sort");
        System.out.println("3. Update/Delete");
        System.out.println("4. Report");
        System.out.println("5. Exit");
        System.out.print("Please choose 1 to Create, 2 to Find and Sort, "
                + "3 to Update/Delete, 4 to Report and 5 to Exit program: ");
    }

    /**
     * Chuc nang 1: Tao sinh vien.
     * Neu so sinh vien hien co > 10 thi hoi nguoi dung co tiep tuc khong.
     */
    public static void createStudent(ArrayList<Student> ls) {
        //Neu so sinh vien > 10 thi hoi tiep tuc
        if (ls.size() > 10) {
            System.out.print("Do you want to continue (Y/N): ");
            if (!Validation.checkInputYN()) {
                return;
            }
        }
        //Lap den khi nhap duoc sinh vien khong trung
        while (true) {
            System.out.print("Enter id: ");
            String id = Validation.checkInputString();
            System.out.print("Enter name student: ");
            String name = Validation.checkInputString();
            //Mot id chi thuoc ve 1 ten sinh vien
            if (Validation.isIdUsedByOtherName(ls, id, name)) {
                System.err.println("Id has exist student. Please re-input.");
                continue;
            }
            System.out.print("Enter semester: ");
            String semester = Validation.checkInputString();
            System.out.print("Enter name course: ");
            String course = Validation.checkInputCourse();
            //Kiem tra sinh vien da ton tai chua
            if (Validation.isStudentExist(ls, id, name, semester, course)) {
                System.err.println("Duplicate.");
                continue;
            }
            ls.add(new Student(id, name, semester, course));
            System.out.println("Add student success.");
            return;
        }
    }

    /**
     * Chuc nang 2: Tim sinh vien theo ten (hoac 1 phan ten) va sap xep theo ten.
     */
    public static void findAndSort(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.print("Enter name to search: ");
        String name = Validation.checkInputString();
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : ls) {
            if (student.getStudentName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        if (result.isEmpty()) {
            System.err.println("Not exist.");
            return;
        }
        //Sap xep theo ten tang dan
        Collections.sort(result, Student.byName);
        System.out.printf("%-15s%-15s%-15s\n", "Student name", "semester", "Course Name");
        for (Student student : result) {
            student.print();
        }
    }

    /**
     * Chuc nang 3: Tim sinh vien theo id roi update hoac delete.
     */
    public static void updateOrDelete(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.print("Enter id: ");
        String id = Validation.checkInputString();
        ArrayList<Student> found = getListStudentById(ls, id);
        if (found.isEmpty()) {
            System.err.println("Not found student.");
            return;
        }
        Student student = getStudentByListFound(found);
        System.out.print("Do you want to update (U) or delete (D) student: ");
        if (Validation.checkInputUD()) {
            //Update
            System.out.print("Enter id: ");
            String newId = Validation.checkInputString();
            System.out.print("Enter name student: ");
            String name = Validation.checkInputString();
            System.out.print("Enter semester: ");
            String semester = Validation.checkInputString();
            System.out.print("Enter name course: ");
            String course = Validation.checkInputCourse();
            //Kiem tra co thay doi khong
            if (isSameInformation(student, newId, name, semester, course)) {
                System.err.println("Nothing change.");
                return;
            }
            //Kiem tra trung voi sinh vien khac khong
            if (Validation.isStudentExist(ls, newId, name, semester, course)) {
                System.err.println("Duplicate.");
                return;
            }
            student.setId(newId);
            student.setStudentName(name);
            student.setSemester(semester);
            student.setCourseName(course);
            System.out.println("Update success.");
        } else {
            //Delete
            ls.remove(student);
            System.out.println("Delete success.");
        }
    }

    /**
     * Lay danh sach sinh vien theo id.
     */
    public static ArrayList<Student> getListStudentById(ArrayList<Student> ls, String id) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())) {
                result.add(student);
            }
        }
        return result;
    }

    /**
     * Cho nguoi dung chon 1 sinh vien trong danh sach tim duoc.
     */
    public static Student getStudentByListFound(ArrayList<Student> found) {
        System.out.println("List student found: ");
        System.out.printf("%-10s%-15s%-15s%-15s\n", "Number", "Student name",
                "semester", "Course Name");
        for (int i = 0; i < found.size(); i++) {
            Student student = found.get(i);
            System.out.printf("%-10d%-15s%-15s%-15s\n", i + 1,
                    student.getStudentName(), student.getSemester(),
                    student.getCourseName());
        }
        System.out.print("Enter student: ");
        int choice = Validation.checkInputIntLimit(1, found.size());
        return found.get(choice - 1);
    }

    /**
     * Chuc nang 4: Bao cao so luong mon hoc cua tung sinh vien.
     * Vi du: Nguyen Van A | Java | 2
     */
    public static void report(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        //key = "ten sinh vien - ten mon", value = so lan hoc mon do
        Map<String, Report> map = new LinkedHashMap<>();
        for (Student student : ls) {
            String key = student.getStudentName().toLowerCase() + "|"
                    + student.getCourseName().toLowerCase();
            Report report = map.get(key);
            if (report == null) {
                map.put(key, new Report(student.getStudentName(),
                        student.getCourseName(), 1));
            } else {
                report.setTotalCourse(report.getTotalCourse() + 1);
            }
        }
        //In bao cao
        for (Report report : map.values()) {
            System.out.printf("%-15s|%-10s|%-5d\n", report.getStudentName(),
                    report.getCourseName(), report.getTotalCourse());
        }
    }

    /**
     * Kiem tra thong tin moi co giong thong tin cu khong.
     *
     * @return true neu khong co gi thay doi.
     */
    private static boolean isSameInformation(Student student, String id,
            String name, String semester, String course) {
        return id.equalsIgnoreCase(student.getId())
                && name.equalsIgnoreCase(student.getStudentName())
                && semester.equalsIgnoreCase(student.getSemester())
                && course.equalsIgnoreCase(student.getCourseName());
    }
}
