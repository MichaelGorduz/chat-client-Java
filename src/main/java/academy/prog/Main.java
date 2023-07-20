package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();

			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

			System.out.println("Enter '@recipient', '/list' to view online users, or hit 'Enter' to send to All: ");
			String desiredRecipient = scanner.nextLine();

			if (desiredRecipient.equals("/list")) {
				displayOnlineUsers();
				System.out.println("Enter @recepient");
				desiredRecipient = scanner.nextLine();
			}

			if (desiredRecipient.isEmpty()) {
				desiredRecipient = "@All";
			}

			System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) {
					break;
				}

				Message message;
				if (desiredRecipient != null) {
					message = new Message(login, desiredRecipient, text);
				} else {
					message = new Message(login, text);
				}

				try {
					int res = message.send(Utils.getURL() + "/add", desiredRecipient);
					System.out.println("Message sent. Response code: " + res);

					if (res != 200) { // Check the response code within the try block
						System.out.println("HTTP error occurred: " + res);
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} finally {
			scanner.close();
		}
	}

	private static void displayOnlineUsers() {
		// Simulate getting a list of online users
		String[] onlineUsers = {"@John", "@Mary", "@Marc", "@Jenny", "@Jason"};

		System.out.println("Online Users:");
		for (String user : onlineUsers) {
			System.out.println(user);
		}
	}
}
