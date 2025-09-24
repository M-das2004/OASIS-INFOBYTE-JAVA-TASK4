import java.util.Scanner;

public class OnlineExamSystem {
	private String username;
	private String password;
	private boolean isLoggedIn;
	private int timeRemaining;
	private int questionCount;
	private int[] userAnswers;
	private int[] correctAnswers;

	public OnlineExamSystem(String username, String password) {
		this.username = username;
		this.password = password;
		System.out.println("Successfully registered! :)");
		this.isLoggedIn = false;
		this.timeRemaining = 10; // in minutes
		this.questionCount = 10;
		this.userAnswers = new int[questionCount];
		this.correctAnswers = new int[questionCount];

		// initialize correct answers with random values (1 or 2)
		for (int i = 0; i < questionCount; i++) {
			correctAnswers[i] = (int) (Math.random() * 2) + 1;
		}
	}

	public void login(Scanner scanner) {
		System.out.println("\nLog in to take the exam:");
		System.out.print("Username: ");
		String inputUsername = scanner.nextLine();
		System.out.print("Password: ");
		String inputPassword = scanner.nextLine();
		if (inputUsername.equals(username) && inputPassword.equals(password)) {
			isLoggedIn = true;
			System.out.println("Login successful. Best of luck!");
		} else {
			System.out.println("Login failed. Please try again.");
		}
	}

	public void logout() {
		isLoggedIn = false;
		System.out.println("Logout successful.\n");
	}

	public void updateProfile(Scanner scanner) {
		if (!isLoggedIn) {
			System.out.println("Please login first.");
			return;
		}
		System.out.print("Enter new username: ");
		username = scanner.nextLine();
		System.out.print("Enter new password: ");
		password = scanner.nextLine();
		System.out.println("Profile updated successfully.");
	}

	public void startExam(Scanner scanner) {
		if (!isLoggedIn) {
			System.out.println("Please login first.");
			return;
		}
		System.out.println("\nYou have " + timeRemaining + " minutes to complete the exam.");
		for (int i = 0; i < questionCount; i++) {
			System.out.println("\nQuestion " + (i + 1) + ":");
			System.out.println("1. Option 1");
			System.out.println("2. Option 2");
			System.out.print("Your answer (1 or 2): ");
			int answer;
			while (true) {
				answer = scanner.nextInt();
				if (answer == 1 || answer == 2) {
					break;
				} else {
					System.out.print("Invalid choice. Please enter 1 or 2: ");
				}
			}
			userAnswers[i] = answer;
		}

		System.out.println("\nWould you like to submit?");
		System.out.println("1: Yes");
		System.out.println("2: No (Auto-submit after time runs out)");
		int choice = scanner.nextInt();
		scanner.nextLine(); // consume newline

		if (choice == 1) {
			submitExam();
		} else {
			System.out.println("Waiting for " + timeRemaining + " minutes before auto-submit...");
			try {
				Thread.sleep(timeRemaining * 60 * 1000); // convert minutes to milliseconds
			} catch (InterruptedException e) {
				System.out.println("Timer interrupted. Auto-submitting now.");
			}
			submitExam();
		}
	}

	public void submitExam() {
		if (!isLoggedIn) {
			System.out.println("Please login first.");
			return;
		}
		int score = 0;
		for (int i = 0; i < questionCount; i++) {
			if (userAnswers[i] == correctAnswers[i]) {
				score++;
			}
		}
		System.out.println("\nYour score is " + score + " out of " + questionCount + ".");
		System.out.println("Thanks for taking the exam. Best of luck! :)");
		logout();
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Registration
		System.out.println("Welcome to the Online Examination System");
		System.out.print("Enter a username to register: ");
		String uName = scanner.nextLine();
		System.out.print("Enter a password to register: ");
		String pWord = scanner.nextLine();

		OnlineExamSystem examSystem = new OnlineExamSystem(uName, pWord);

		// Login
		while (!examSystem.isLoggedIn) {
			examSystem.login(scanner);
		}

		// Menu loop
		int choice = -1;
		while (examSystem.isLoggedIn) {
			System.out.println("\n--- MENU ---");
			System.out.println("1. Update Profile");
			System.out.println("2. Start Exam");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number.");
				continue;
			}

			switch (choice) {
				case 1:
					examSystem.updateProfile(scanner);
					break;
				case 2:
					examSystem.startExam(scanner);
					break;
				case 3:
					examSystem.logout();
					break;
				default:
					System.out.println("Invalid choice. Try again.");
			}
		}
		scanner.close();
	}
}