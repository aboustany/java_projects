package ContactList.controller;

import ContactList.dao.ClassRosterDao;
import ContactList.dao.ClassRosterDaoException;
import ContactList.dao.ClassRosterDaoFileImpl;
import ContactList.dto.Student;
import ContactList.ui.ClassRosterView;
import ContactList.ui.UserIO;
import ContactList.ui.UserIOConsoleImpl;

import java.util.List;

public class ClassRosterController {

    private ClassRosterView view;
    private ClassRosterDao dao;
    private UserIO io = new UserIOConsoleImpl();

    public ClassRosterController(ClassRosterDao dao, ClassRosterView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        io.print("LIST STUDENTS");
                        listStudents();
                        break;
                    case 2:
                        io.print("CREATE STUDENT");
                        createStudent();
                        break;
                    case 3:
                        io.print("VIEW STUDENT");
                        viewStudent();
                        break;
                    case 4:
                        io.print("REMOVE STUDENT");
                        removeStudent();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                        break;
                }

            }
            exitMessage();
        }catch (ClassRosterDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void createStudent() throws ClassRosterDaoException {
        view.displayCreateStudentBanner();
        Student newStudent = view.getNewStudentInfo();
        dao.addStudent(newStudent.getStudentId(), newStudent);
        view.displayCreateSuccessBanner();
    }

    private void listStudents() throws ClassRosterDaoException {
        view.displayDisplayAllBanner();
        List<Student> studentList = dao.getAllStudents();
        view.displayStudentList(studentList);
    }

    public void viewStudent() throws ClassRosterDaoException {
        view.displayDisplayStudentBanner();
        String studentId = view.getStudentIdChoice();
        Student student = dao.getStudent(studentId);
        view.displayStudent(student);
    }

    private void removeStudent() throws ClassRosterDaoException {
        view.displayDeleteStudentBanner();
        String studentId = view.getStudentIdChoice();
        Student removedStudent = dao.removeStudent(studentId);
        view.displayRemoveResult(removedStudent);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    private void exitMessage() {
        view.displayExitBanner();
    }

}