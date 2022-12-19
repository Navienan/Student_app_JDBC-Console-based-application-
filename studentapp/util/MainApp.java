package com.ms.studentapp.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ms.studentapp.dao.StudentDatabaseOperation;
import com.ms.studentapp.dto.Student;

public class MainApp {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {

		StudentDatabaseOperation operation = new StudentDatabaseOperation();

		System.out.println("************Welcome************");

		while (true) {
			System.out.println("___________________________________________");
			System.out.println("1.Register\n2.Login\n3.Forget Password\n4.Exit");
			System.out.println("___________________________________________");
			System.out.println("Enter your choice");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				// Register
				Student s = new Student();

				System.out.println("Enter your ID :");
				int id = sc.nextInt();
				System.out.println("Enter your Name :");
				String name = sc.next();
				System.out.println("Enter your Marks :");
				double marks = sc.nextDouble();
				System.out.println("Enter your Email Id :");
				String mailid = sc.next();
				System.out.println("Enter your Password :");
				String password = sc.next();

				s.setId(id);
				s.setName(name);
				s.setMarks(marks);
				s.setEmailId(mailid);
				s.setPassword(password);

				boolean isInsetred = operation.insertData(s);
				if (isInsetred) {
					System.out.println("Student Registered:)");
				} else {
					System.out.println("Student Not Registered:(");
				}

				break;
			case 2:
				// Login
				System.out.println("Enter the Email Id");
				String mail = sc.next();
				System.out.println("Enter the Password");
				String pass = sc.next();
				Student loggedInStudent = operation.loginValidate(mail, pass);

				if (loggedInStudent == null) {
					System.out.println("Invalid Credientials *_* Check Email/Password");
				} else {
					System.out.println("WELCOME:)" + loggedInStudent.getName());

					while (true) {
						System.out.println("___________________________________________");
						System.out.println(
								"1.Display All Students\n2.Search by ID\n3.Update\n4.Delete\n5.Display Based on Marks\n6.Logout");
						System.out.println("___________________________________________");
						System.out.println("Enter your Choice");
						int subMenuChoice = sc.nextInt();
						if (subMenuChoice == 1) {
							// Display All Students
							ArrayList<Student> allStudents = operation.getAllStudents();

							System.out.println("ID\t\tNAME\t\tMARKS\t\tEMAIL");

							for (Student s1 : allStudents) {
								System.out.println(s1.getId() + "\t\t" + s1.getName() + "\t\t" + s1.getMarks() + "\t\t"
										+ s1.getEmailId());
							}

						} else if (subMenuChoice == 2) {
							// Search
							System.out.println("Enter Student ID");
							int stuId = sc.nextInt();
							Student searchedStudent = operation.getStudent(stuId);

							if (searchedStudent == null) {
								System.out.println("No Records Found For ID " + stuId + " +_+");
							} else {
								System.out.println("ID\t\tNAME\t\tMARKS\t\tEMAIL");
								System.out.println(searchedStudent.getId() + "\t\t" + searchedStudent.getName() + "\t\t"
										+ searchedStudent.getMarks() + "\t\t" + searchedStudent.getEmailId());
							}

						} else if (subMenuChoice == 3) {
							// Update
							System.out.println("Enter Student ID to be updtaed");
							int sid = sc.nextInt();
							Student stu = operation.getStudent(sid);
							if (stu == null) {
								System.out.println("Data cannot be updated because Data not found for ID " + sid);
							} else {
								// update
								int beforeUpdate = stu.hashCode();
								System.out.println("----------------------------------");
								System.out.println("a.Name\nb.Marks\nc.Email ID");
								System.out.println("----------------------------------");
								char updatechoice = sc.next().charAt(0);

								if (updatechoice == 'a') {
									System.out.println("Enter Updated Name");
									String updatedName = sc.next();
									stu.setName(updatedName);
								} else if (updatechoice == 'b') {
									System.out.println("Enter Updated Marks");
									double updateMarks = sc.nextDouble();
									stu.setMarks(updateMarks);
								} else if (updatechoice == 'c') {
									System.out.println("Enter Updated Email");
									String updatedEmail = sc.next();
									stu.setEmailId(updatedEmail);
								} else {
									System.out.println("Invalid Choice");
								}
								int afterUpdate = stu.hashCode();
								if (beforeUpdate != afterUpdate) {
									boolean updated = operation.updateStudent(stu);
									if (updated) {
										System.out.println("Data Updated :)");
									} else {
										System.out.println("Data Not updated :(");
									}
								}
							}

						} else if (subMenuChoice == 4) {
							// Delete

							System.out.println("Enter the ID:");
							int deleteId = sc.nextInt();

							Student deleteStudent = operation.getStudent(deleteId);
							if (deleteStudent == null)
								System.out.println("Data not deleted because ID not found " + deleteId);
							else {
								boolean isDeleted = operation.deleteStudent(deleteId);
								if (isDeleted)
									System.out.println("Data is deleted...");
								else
									System.out.println("Data is not deleted...");
							}
						} else if (subMenuChoice == 5) {
							// Display Based on Marks
							System.out.println("Enter the lowest Value");
							int lwstvalue = sc.nextInt();
							System.out.println("Enter the Highest Value");
							int histvalue = sc.nextInt();
							ArrayList<Student> studentbymarks = operation.getAllStudentsByMarks(lwstvalue, histvalue);
							if (studentbymarks.isEmpty()) {
								System.out.println(
										"No students found between " + lwstvalue + " and " + histvalue + " range");
							} else {
								System.out.println("ID\t\tNAME\t\tMARKS\t\tEMAIL");
								for (Student mrks : studentbymarks) {
									System.out.println(mrks.getId() + "\t\t" + mrks.getName() + "\t\t" + mrks.getMarks()
											+ "\t\t" + mrks.getEmailId());
								}
							}
						} else if (subMenuChoice == 6) {
							// Logout
							System.out.println("Thank You!!!!" + " " + loggedInStudent.getName());
							break;
						}

					}
				}

				break;
			case 3:
				// Forget Password
				System.out.println("Enter Email ID");
				String mailId = sc.next();
				System.out.println("Enter the New Password");
				String newPassword = sc.next();
				System.out.println("Re-type the password");
				String reTypePassword = sc.next();
				while (!newPassword.equals(reTypePassword)) {
					System.out.println("New Password and Re-type Password is not Matching");
					System.out.println("Enter the New Password");
					newPassword = sc.next();
					System.out.println("Re-type the password");
					reTypePassword = sc.next();
				}
				Boolean isUpdated = operation.updatePassword(mailId, newPassword);
				if (isUpdated) {
					System.out.println("PAssword is Updated :)");
				} else {
					System.out.println("Something Went Wrong While Updated *_*");
				}

				break;
			case 4:
				System.out.println("************Thank You************");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid Choice!!!!!!");
				break;
			}

		}

	}

}
