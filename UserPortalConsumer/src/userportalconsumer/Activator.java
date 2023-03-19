package userportalconsumer;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import phonenetworkprovider.stores.Database;
import phonenetworkprovider.models.*;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference<?> reference;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		try {
			Activator.context = bundleContext;
			System.out.println("Starting User Portal Consumer...");
			reference = bundleContext.getServiceReference(Database.class.getName());
			Database db = (Database) context.getService(reference);

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			int decision;

			System.out.println("===== Welcome to the User Portal =====");
			System.out.println("Please navigate the menu using the number of the decision");
			System.out.println("1. Create a new user");
			System.out.println("2. Continue as existing user");

			while (true) {
				try {
					decision = scanner.nextInt();
					if (decision > 2 || decision < 1) {
						throw new IllegalStateException(String.format("%d is not a decision", decision));
					}
					break;
				} catch (Exception e) {
					scanner.nextLine();
					System.out.println("Invalid input! Please try again");
				}
			}

			if (decision == 1) {
				scanner.nextLine();
				String serviceProviderId = "";
				String mobile = "";
				String name = "";
				String address = "";

				System.out.println("==== Account Registration ====");
				System.out.print("Please enter your name : ");
				name = scanner.nextLine();
				System.out.print("Please enter your address : ");
				address = scanner.nextLine();
				System.out.println("Please pick a service provider from the following list, ");

				var serviceProviders = db.getServiceProviders();

				// If service providers are not empty, continue the register process
				if (serviceProviders.isEmpty()) {
					System.out.println("There are currently no service providers. please try again later");
				} else {
					System.out.println("=====================================================");
					System.out.println("= Id          = Name           = Location(s)        =");
					System.out.println("=====================================================");
					serviceProviders
							.forEach(item -> System.out.println(String.format("= %-11.11s = %-14.14s = %-18.18s =",
									item.getId(), item.getName(), item.getLocation())));
					System.out.println("=====================================================\n");

					// Re-prompt for input until valid name received
					while (true) {
						System.out.print("Enter the name of the required service provider : ");
						try {
							final String temp = scanner.nextLine();
							IServiceProvider selectedServiceProvider = serviceProviders.stream()
									.filter(item -> item.getName().equalsIgnoreCase(temp)).findFirst()
									.orElseThrow(IllegalStateException::new);
							serviceProviderId = selectedServiceProvider.getId();
							mobile = db.generateUniquePhoneNumber(selectedServiceProvider.getDigits());
							break;
						} catch (Exception e) {
							System.out.println("Invalid input! Please try again");
						}
					}

					// Save the newly created user
					IUser user = new User(name, address, serviceProviderId, mobile);
					db.addUser(user);

					System.out.println("Your phone number is: " + mobile);
					System.out.println("All done! You can now log in using the phone number you've received");
				}
			} else if (decision == 2) {
				scanner.nextLine();

				IUser user;
				var users = db.getUsers();

				// Prompt to get user's number
				while (true) {
					System.out.print("Please enter your mobile number : ");
					try {
						final String temp = scanner.nextLine();
						user = users.stream().filter(item -> item.getMobile().equals(temp)).findFirst()
								.orElseThrow(IllegalStateException::new);
						break;
					} catch (Exception e) {
						System.out.println("This number does not exist! Please try again");
					}
				}

				// Show user management menu
				boolean isExitting = false;
				while (!isExitting) {
					System.out.println("===== Welcome " + user.getName() + " =====");
					System.out.println("What would you like to do today?");
					System.out.println("1. Add a plan");
					System.out.println("2. Remove plan");
					System.out.println("3. See current plans");
					System.out.println("4. See service provider details");
					System.out.println("5. Lodge a complaint");
					System.out.println("6. Send some feedback");
					System.out.println("7. Delete number");
					System.out.println("8. Exit user manager");

					while (true) {
						try {
							decision = scanner.nextInt();
							if (decision > 8 || decision < 1) {
								throw new IllegalStateException(String.format("%d is not a decision", decision));
							}
							break;
						} catch (Exception e) {
							System.out.println("Invalid input! Please try again");
						}
					}

					List<IDataPlan> dataPlans;
					final IUser finalUser = user;

					// For cases 5, 6
					String messageDescription;

					switch (decision) {
					case 1:
						// Will display the list of plans if it exists and allow user to add one.
						System.out.println("== Add a plan ==");
						dataPlans = db.getDataPlans().stream()
								.filter(item -> item.getServiceProviderId().equals(finalUser.getServiceProviderId()))
								.toList();
						if (dataPlans.isEmpty()) {
							System.out.println(
									"There are currently no data plans offered by this service provider. Please try again later");
						} else {
							System.out.println("= Available Data Plans =");
							System.out.println("==============================================");
							System.out.println("= #   = Id          = Name                	 =");
							System.out.println("==============================================");
							IntStream.range(0, dataPlans.size())
									.forEach(idx -> System.out.println(String.format("= %3.3s = %8.8s...  = %22.22s =",
											idx, dataPlans.get(idx).getId(), dataPlans.get(idx).getName())));
							System.out.println("=====================================================\n");
							System.out.print("Please enter the # of the data plan you want : ");

							int chosenIdx = 0;
							while (true) {
								try {
									decision = scanner.nextInt();
									chosenIdx = decision;
									if (decision < -1 || decision >= dataPlans.size()) {
										throw new IllegalStateException(
												String.format("%d is not a decision", decision));
									}
									break;
								} catch (Exception e) {
									System.out.println("Invalid data plan number! Please try again");
								}
							}

							// See extra details about the plan
							IDataPlan selectedDataPlan = dataPlans.get(decision);
							System.out.println(String.format("* %22.22s \n* %f", selectedDataPlan.getName(),
									selectedDataPlan.getPrice()));
							System.out.println(String.format(selectedDataPlan.getDescription()));
							System.out.println("Do you want to add this plan ?");
							System.out.println("1. Yes");
							System.out.println("2. No");

							while (true) {
								try {
									decision = scanner.nextInt();
									if (decision < 1 || decision > 2) {
										throw new IllegalStateException(
												String.format("%d is not a decision", decision));
									}
									break;
								} catch (Exception e) {
									System.out.println("Invalid input! Please try again");
								}
							}

							if (decision == 1) {
								// Add data plan id to user and save.
								String selectedDataPlanId = dataPlans.get(chosenIdx).getId();
								user.addPlan(selectedDataPlanId);

								System.out.println("Succesfully added data plan!");
							} else {
								System.out.println("Stopping add a plan proces...");
							}
						}
						break;
					case 2:
						// Will remove plan from user if it exists
						System.out.println("== Remove a plan ==");
						dataPlans = db.getDataPlans().stream()
								.filter(item -> item.getServiceProviderId().equals(finalUser.getServiceProviderId()))
								.toList();
						if (dataPlans.isEmpty()) {
							System.out.println("You currently have no plans. Please add one to see them here");
						} else {
							System.out.println("= Available Data Plans =");
							System.out.println("==============================================");
							System.out.println("= #   = Id          = Name                   =");
							System.out.println("==============================================");
							IntStream.range(0, dataPlans.size())
									.forEach(idx -> System.out.println(String.format("= %3.3s = %8.8s...  = %22.22s =",
											idx, dataPlans.get(idx).getId(), dataPlans.get(idx).getName())));
							System.out.println("=====================================================\n");
							System.out.print("Please enter the # of the data plan you want : ");

							int chosenIdx = 0;
							while (true) {
								try {
									decision = scanner.nextInt();
									chosenIdx = decision;
									if (decision < -1 || decision >= dataPlans.size()) {
										throw new IllegalStateException(
												String.format("%d is not a decision", decision));
									}
									break;
								} catch (Exception e) {
									System.out.println("Invalid data plan number! Please try again");
								}
							}

							String selectedDataPlanId = dataPlans.get(chosenIdx).getId();
							user.removePlan(selectedDataPlanId);

							System.out.println("Succesfully removed data plan!");
						}
						break;
					case 3:
						System.out.println("== Current plans ==");
						dataPlans = db.getDataPlans().stream()
								.filter(item -> item.getServiceProviderId().equals(finalUser.getServiceProviderId()))
								.toList();
						if (dataPlans.isEmpty()) {
							System.out.println("You currently have no plans. Please add one tosee them here.");
						} else {
							System.out.println("==============================================");
							System.out.println("= #   = Id          = Name                	 =");
							System.out.println("==============================================");
							IntStream.range(0, dataPlans.size())
									.forEach(idx -> System.out.println(String.format("= %3.3s = %8.8s...  = %22.22s =",
											idx, dataPlans.get(idx).getId(), dataPlans.get(idx).getName())));
							System.out.println("=====================================================\n");
						}
						break;
					case 4:
						var serviceProvider = user.getProvider();

						System.out.println("== Service provider details ==");
						System.out.println(String.format("Id * %s \nName * %s \nLocation * %s", serviceProvider.getId(),
								serviceProvider.getName(), serviceProvider.getLocation()));
						break;
					case 5:
						scanner.nextLine();
						System.out.println("== Lodge a complaint ==");
						System.out.println("Please type your complaint below, ");

						while (true) {
							try {
								messageDescription = scanner.nextLine();
								if (messageDescription.isBlank())
									throw new IllegalStateException();
								break;
							} catch (Exception e) {
								System.out.println("Invalid Complaint! Complaint cannot be empty");
							}
						}

						db.createMessage(new Message(MessageType.Complaint, user.getId(), messageDescription));
						System.out.println("Complaint has been succesfully sent");
						break;
					case 6:
						scanner.nextLine();
						System.out.println("== Send some feedback ==");
						System.out.println("Please type your feedback below, ");

						while (true) {
							try {
								messageDescription = scanner.nextLine();
								if (messageDescription.isBlank())
									throw new IllegalStateException();
								break;
							} catch (Exception e) {
								System.out.println("Invalid Feedback! Feedback cannot be empty");
							}
						}

						db.createMessage(new Message(MessageType.Feedback, user.getId(), messageDescription));
						System.out.println("Feedback has been succesfully sent");
						break;
					case 7:
						System.out.println("== Are you sure you want to delete your number ? ==");
						System.out.println(
								"This process is irreversible. You will not be able to retrieve this user again ");

						System.out.println("1. Yes, Delete this user");
						System.out.println("2. No, Don't delete this user");

						while (true) {
							try {
								decision = scanner.nextInt();
								if (decision < 1 || decision > 2) {
									throw new IllegalStateException(String.format("%d is not a decision", decision));
								}
								break;
							} catch (Exception e) {
								System.out.println("Invalid data plan number! Please try again");
							}
						}

						if (decision == 1) {
							System.out.println("Deleting user...");
							db.deleteMessagesByUser(user.getId());
							db.removeUser(user.getId());
							isExitting = true;
						} else if (decision == 2) {
							System.out.println("Cancelling deletion...");
						}

						// Delete messages of user, then delete user

						break;
					case 8:
					default:
						System.out.println("Exitting User Portal Consumer...");
						isExitting = true;
						break;
					}
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stopping User Portal Consumer...");
		context.ungetService(reference);
		Activator.context = null;
	}
}
