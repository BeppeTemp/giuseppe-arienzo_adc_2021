package it.isislab.p2p.git;

import java.nio.file.Paths;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import it.isislab.p2p.git.implementations.GitProtocolImpl;

/**
 * docker build --no-cache -t test . docker run -i -e MASTERIP="127.0.0.1" -e
 * ID=0 test use -i for interactive mode use -e to set the environment variables
 * 
 * @author carminespagnuolo
 *
 */
public class Tester {

	@Option(name = "-m", aliases = "--masterip", usage = "Ip del master peer", required = true)
	private static String master;

	@Option(name = "-id", aliases = "--identifierpeer", usage = "L'identificativo univoco del peer", required = true)
	private static int id;

	private static void printMenu() {
		System.out.println("🍳 Menu: ");
		System.out.println(" 1 - Crea una Repository");
		System.out.println(" 2 - Clona una Repository");
		System.out.println(" 3 - Aggiungi file a una repository");
		System.out.println(" 4 - Commit");
		System.out.println(" 5 - Push");
		System.out.println(" 6 - Pull");
		System.out.println(" 8 - Mostra lo stato di una repository remota");
		System.out.println(" 9 - Mostra lo stato di una repository locale");
		System.out.println("10 - Mostra commits locali");
		System.out.println("11 - Elimina repository");
		System.out.println("12 - Esci 🚪");
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		final CmdLineParser parser = new CmdLineParser(new Tester());

		try {
			parser.parseArgument(args);
			TextIO textIO = TextIoFactory.getTextIO();
			GitProtocolImpl peer = new GitProtocolImpl(id, master);

			System.out.println("\nPeer: " + id + " on Master: " + master + " \n");

			boolean flag = true;
			String repo_name;
			String dir;

			while (flag) {
				printMenu();
				int option = textIO.newIntInputReader().withMaxVal(12).withMinVal(1).read("Option");

				switch (option) {
				case 1:
					repo_name = textIO.newStringInputReader().read("Nome della Repository:");
					dir = textIO.newStringInputReader().read("Directory della repository:");

					if (peer.createRepository(repo_name, Paths.get(dir)))
						System.out.println("\nRepository \"" + repo_name + "\" creata con successo ✅\n");
					else
						System.out.println("\nErrore nella creazione della repository ❌\n");

					break;

				case 2:
					repo_name = textIO.newStringInputReader().read("Nome della Repository:");
					dir = textIO.newStringInputReader().withDefaultValue(".").read("Directory di destinazione:");

					if (peer.clone(repo_name, Paths.get(dir)))
						System.out.println("\nRepository \"" + repo_name + "\" clonata correttamente  ✅\n");
					else
						System.out.println("\nErrore nel clonare la repository ❌\n");
					break;

				case 3:
					repo_name = textIO.newStringInputReader().read("Nome della Repository:");
					dir = textIO.newStringInputReader().read("Directory da aggiungere:");

					if (peer.addFilesToRepository(repo_name, Paths.get(dir)))
						System.out.println("\nFile correttamente aggiunti alla repository \"" + repo_name + "\" ✅\n");
					else
						System.out.println("\nErrore nell'aggiunta dei file ❌\n");

					break;

				case 4:
					repo_name = textIO.newStringInputReader().read("Nome della Repository:");
					String message = textIO.newStringInputReader().withDefaultValue("Ho cambiato qualcosa 🤷").read("Messaggio:");

					if (peer.commit(repo_name, message))
						System.out.println("\nCommit sulla repository \"" + repo_name + "\" creato correttamente ✅\n");
					else
						System.out.println("\nNessuna modifica trovata ❌\n");
					break;

				case 5:
					repo_name = textIO.newStringInputReader().read("Nome della Repository:");
					System.out.println(peer.push(repo_name));
					break;

				case 6:
					repo_name = textIO.newStringInputReader().withDefaultValue("my_new_repository").read("Nome della Repository:");
					System.out.println(peer.pull(repo_name));
					break;

				case 7:
					repo_name = textIO.newStringInputReader().read("Nome della Repository:");

					if (peer.removeRepo(repo_name))
						System.out.println("\nRepository \"" + repo_name + "\" correttamente eliminata ✅\n");
					else
						System.out.println("\nErrore nell'eliminazione della repository ❌\n");
					break;

				case 8:
					repo_name = textIO.newStringInputReader().read("Nome della Repository:");
					peer.show_Remote_Repo(repo_name);
					break;

				case 9:
					repo_name = textIO.newStringInputReader().read("Nome della Repository:");
					peer.show_Local_Repo(repo_name);
					break;

				case 10:
					peer.show_Local_Commits();
					break;

				case 11:
					if (peer.leaveNetwork()) {
						System.out.println("\nDisconnessione completata ✅\n");
						flag = false;
					} else
						System.out.println("\nErrore nella disconessione ❌\n");
					break;

				case 12:
					flag = false;
					break;

				default:
					break;
				}
			}
		} catch (CmdLineException clEx) {
			System.err.println("ERRORE: Impossibile completare il parsinge delle opzioni: " + clEx);
		}
	}
}