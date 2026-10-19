package Manager;

import Entity.Student;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cac ham nhap lieu va kiem tra du lieu nguoi dung.
 *
 * @author THAYCACAC
 */
public class Validation {

    private static final Scanner in = new Scanner(System.in);

    //Nhap so nguyen trong doan [min, max]
    public static int checkInputIntLimit(int min, int max) {
        while (true) {
            try {
                int result = Integer.parseInt(in.nextLine().trim());
                if (result < min || result > max) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input number in range [" + min + ", " + max + "]");
                System.out.print("Enter again: ");
            }
        }
    }

    //Nhap chuoi khong duoc de trong
    public static String checkInputString() {
        while (true) {
            String result = in.nextLine().trim();
            if (result.isEmpty()) {
                System.err.println("Not empty");
                System.out.print("Enter again: ");
            } else {
                return result;
            }
        }
    }

    //Nhap Y/N
    public static boolean checkInputYN() {
        while (true) {
            String result = checkInputString();
            if (result.equalsIgnoreCase("Y")) {
                return true;
            }
            if (result.equalsIgnoreCase("N")) {
                return false;
            }
            System.err.println("Please input y/Y or n/N.");
            System.out.print("Enter again: ");
        }
    }

    //Nhap U/D
    public static boolean checkInputUD() {
        while (true) {
            String result = checkInputString();
            if (result.equalsIgnoreCase("U")) {
                return true;
            }
            if (result.equalsIgnoreCase("D")) {
                return false;
            }
            System.err.println("Please input u/U or d/D.");
            System.out.print("Enter again: ");
        }
    }

    //Nhap ten mon hoc: chi co 3 mon Java, .Net, C/C++
    public static String checkInputCourse() {
        while (true) {
            String result = checkInputString();
            if (result.equalsIgnoreCase("java")
                    || result.equalsIgnoreCase(".net")
                    || result.equalsIgnoreCase("c/c++")) {
                return result;
            }
            System.err.println("There are only three courses: Java, .Net, C/C++");
            System.out.print("Enter again: ");
        }
    }

    /**
     * Kiem tra sinh vien da ton tai chua (trung ca id, ten, semester, course).
     *
     * @return true neu da ton tai (bi trung), false neu chua ton tai.
     */
    public static boolean isStudentExist(ArrayList<Student> ls, String id,
            String studentName, String semester, String courseName) {
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())
                    && studentName.equalsIgnoreCase(student.getStudentName())
                    && semester.equalsIgnoreCase(student.getSemester())
                    && courseName.equalsIgnoreCase(student.getCourseName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kiem tra id da thuoc ve sinh vien khac chua.
     * Mot id chi duoc gan cho 1 ten sinh vien duy nhat.
     *
     * @return true neu id da ton tai voi ten khac (khong hop le).
     */
    public static boolean isIdUsedByOtherName(ArrayList<Student> ls, String id, String name) {
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())
                    && !name.equalsIgnoreCase(student.getStudentName())) {
                return true;
            }
        }
        return false;
    }
}
