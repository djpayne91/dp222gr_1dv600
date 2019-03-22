package hangmanMain;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameWindow extends Application {

	private double xVel;
	private double yVel;
	private int frame;
	private boolean muted = false;
	private HangmanGame game;
	private Stage window;
	private ColorAdjust pressEffect = new ColorAdjust();
	private Category[] categories = { new Category("TV", "tvNames.txt"), new Category("MOVIES", "movieNames.txt") };
	private String category;
	private Image[] guys = { new Image(load("guy6.png")), new Image(load("guy5.png")), new Image(load("guy4.png")),
			new Image(load("guy3.png")), new Image(load("guy2.png")), new Image(load("guy1.png")) };
	private MediaPlayer click = new MediaPlayer(new Media(new File("resources/click.wav").toURI().toString()));
	
	@Override
	public void start(Stage arg0) throws Exception {
		this.window = arg0;

		pressEffect.setBrightness(-0.3);

		window.setOnCloseRequest(e -> {
			confQuit();
			e.consume();
		});
		window.setHeight(510);
		window.setWidth(650);
		window.setResizable(false);
		window.setScene(mainMenuScene());
		window.setTitle("Hangman");
		window.show();
	}

	private Scene mainMenuScene() {

		Pane content = new Pane();
		// Create background

		ImageView menuBackground = new ImageView(new Image(load("mainMenuBackground.png")));
		menuBackground.setFitHeight(510);
		menuBackground.setFitWidth(650);
		// create buttons

		// new game
		ImageView playButton = new ImageView(new Image(load("newGameButton.png")));
		playButton.setX(220);
		playButton.setY(200);
		playButton.setOnMousePressed(e -> {
			playButton.setEffect(pressEffect);
			if (!muted) {
				click.seek(Duration.ZERO);
				click.play();
			}
		});
		playButton.setOnMouseReleased(e -> {
			window.setScene(categoryScene());
		});

		// quit game
		ImageView quitButton = new ImageView(new Image(load("quitButton.png")));
		quitButton.setX(220);
		quitButton.setY(300);
		quitButton.setOnMousePressed(e -> {
			quitButton.setEffect(pressEffect);
			if (!muted) {
				click.seek(Duration.ZERO);
				click.play();
			}
		});
		quitButton.setOnMouseReleased(e -> {
			quitButton.setEffect(null);
			confQuit();
		});

		content.getChildren().addAll(menuBackground, playButton, quitButton);

		return new Scene(content);
	}

	private Scene categoryScene() {

		Pane content = new Pane();
		// create background
		ImageView background = new ImageView(new Image(load("categoryBackground.png")));
		background.setFitHeight(510);
		background.setFitWidth(650);
		// create buttons

		ImageView movieButton = new ImageView(new Image(load("moviesButton.png")));
		movieButton.setX(220);
		movieButton.setY(125);

		ImageView tvButton = new ImageView(new Image(load("tvshowsButton.png")));
		tvButton.setX(220);
		tvButton.setY(225);
		// set button functionality

		movieButton.setOnMousePressed(e -> {
			movieButton.setEffect(pressEffect);
			if (!muted) {
				click.seek(Duration.ZERO);
				click.play();
			}
		});
		movieButton.setOnMouseReleased(e -> {
			category = "MOVIES";
			window.setScene(gameScene(categories[1].getRandomPhrase()));
		});

		tvButton.setOnMousePressed(e -> {
			tvButton.setEffect(pressEffect);
			if (!muted) {
				click.seek(Duration.ZERO);
				click.play();
			}
		});
		tvButton.setOnMouseReleased(e -> {
			category = "TV";

			window.setScene(gameScene(categories[0].getRandomPhrase()));
		});

		content.getChildren().addAll(background, movieButton, tvButton);

		return new Scene(content);
	}

	private Scene gameScene(Phrase newPhrase) {

		game = new HangmanGame(newPhrase);
		VBox content = new VBox();
		// create background
		Pane gamePane = new Pane();
		ImageView background = new ImageView(new Image(load("background.png")));
		Text gameText = new Text(game.gamePhrase.toString());
		gameText.setY(375);
		gameText.setTextAlignment(TextAlignment.CENTER);
		gameText.setWrappingWidth(650);
		gameText.setFont(Font.font(28));

		background.setFitHeight(390);
		background.setFitWidth(650);

		ImageView hangedMan = new ImageView();
		hangedMan.setX(100);
		hangedMan.setY(150);
		hangedMan.setFitHeight(120);
		hangedMan.setFitWidth(150);

		Media loopSound = new Media(new File("resources/musicLoop.wav").toURI().toString());
		MediaPlayer loopMP = new MediaPlayer(loopSound);
		loopMP.setCycleCount(MediaPlayer.INDEFINITE);
		loopMP.setMute(muted);
		loopMP.play();
		
		
		MediaPlayer winMP = new MediaPlayer(new Media(new File("resources/winSound.wav").toURI().toString()));
		MediaPlayer loseMP = new MediaPlayer(new Media(new File("resources/loseSound.wav").toURI().toString()));

		ImageView muteButton = new ImageView(new Image(load("muteButton.png")));
		muteButton.setFitHeight(50);
		muteButton.setFitWidth(50);
		muteButton.setX(585);
		muteButton.setY(5);
		

		if (muted) {
			muteButton.setOpacity(0.5);
		}
		muteButton.setOnMouseClicked(e -> {
			if (!muted) {
				muteButton.setOpacity(.5);
			} else {
				muteButton.setOpacity(1.0);
			}
			muted = !muted;
			click.setMute(muted);
			loopMP.setMute(muted);
			winMP.setMute(muted);
			loseMP.setMute(muted);
		});

		gamePane.getChildren().addAll(background, gameText, hangedMan, muteButton);

		GridPane alphaGrid = new GridPane();
		Button[] alphabet = new Button[26];

		Button a = new Button("A");
		GridPane.setConstraints(a, 0, 0);
		Button b = new Button("B");
		GridPane.setConstraints(b, 1, 0);
		Button c = new Button("C");
		GridPane.setConstraints(c, 2, 0);
		Button d = new Button("D");
		GridPane.setConstraints(d, 3, 0);
		Button e = new Button("E");
		GridPane.setConstraints(e, 4, 0);
		Button f = new Button("F");
		GridPane.setConstraints(f, 5, 0);
		Button g = new Button("G");
		GridPane.setConstraints(g, 6, 0);
		Button h = new Button("H");
		GridPane.setConstraints(h, 7, 0);
		Button i = new Button("I");
		GridPane.setConstraints(i, 8, 0);
		Button j = new Button("J");
		GridPane.setConstraints(j, 9, 0);
		Button k = new Button("K");
		GridPane.setConstraints(k, 10, 0);
		Button l = new Button("L");
		GridPane.setConstraints(l, 11, 0);
		Button m = new Button("M");
		GridPane.setConstraints(m, 12, 0);
		Button n = new Button("N");
		GridPane.setConstraints(n, 0, 1);
		Button o = new Button("O");
		GridPane.setConstraints(o, 1, 1);
		Button p = new Button("P");
		GridPane.setConstraints(p, 2, 1);
		Button q = new Button("Q");
		GridPane.setConstraints(q, 3, 1);
		Button r = new Button("R");
		GridPane.setConstraints(r, 4, 1);
		Button s = new Button("S");
		GridPane.setConstraints(s, 5, 1);
		Button t = new Button("T");
		GridPane.setConstraints(t, 6, 1);
		Button u = new Button("U");
		GridPane.setConstraints(u, 7, 1);
		Button v = new Button("V");
		GridPane.setConstraints(v, 8, 1);
		Button w = new Button("W");
		GridPane.setConstraints(w, 9, 1);
		Button x = new Button("X");
		GridPane.setConstraints(x, 10, 1);
		Button y = new Button("Y");
		GridPane.setConstraints(y, 11, 1);
		Button z = new Button("Z");
		GridPane.setConstraints(z, 12, 1);

		Button[] alphabetTemp = { a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z };

		ImageView ghost = new ImageView(new Image(load("ghost.png")));
		ghost.setX(140);
		ghost.setY(150);
		ghost.setFitHeight(140);
		ghost.setFitWidth(55);
		ghost.setOpacity(0);
		gamePane.getChildren().add(ghost);

		xVel = 1.0;
		yVel = 1.0;
		frame = 0;

		KeyFrame ghostKey = new KeyFrame(Duration.millis(1000.0 / 60.0), eg -> {
			if (frame == 20) {
				ghost.setScaleX(ghost.getScaleX() * -1);
				xVel *= -1;
			}

			if (frame >= 65) {
				ghost.setScaleX(ghost.getScaleX() * -1);
				xVel *= -1;
				frame = 20;
			}
			ghost.setX(ghost.getX() - xVel);
			ghost.setY(ghost.getY() - yVel);
			frame++;
		});
		Timeline ghostTimeline = new Timeline(ghostKey);
		ghostTimeline.setCycleCount(Timeline.INDEFINITE);

		FadeTransition ghostFade = new FadeTransition(Duration.millis(1500), ghost);
		ghostFade.setAutoReverse(true);
		ghostFade.setCycleCount(2);
		ghostFade.setFromValue(0.0);
		ghostFade.setToValue(0.8);

		// sets behavior and properties of alphabet grid
		int alphacount = 0;
		for (Button button : alphabetTemp) {
			alphabet[alphacount] = button;
			char ch = button.getText().charAt(0);
			button.getStylesheets().add("customStyle.css");
			button.setId("letter-button");
			button.setMinSize(47, 40);
			button.setOnAction(ev -> {
				if (!muted) {
					click.seek(Duration.ZERO);
					click.play();
				}

				button.setDisable(true);
				if (!game.gamePhrase.guess(ch)) {
					game.guessesLeft--;
				}

				gameText.setText(game.gamePhrase.toString());
				if (game.guessesLeft < 6 && game.guessesLeft >= 0)
					hangedMan.setImage(guys[game.guessesLeft]);

				if (game.checkWin()) {

					for (Button but : alphabetTemp) {
						but.setDisable(true);
					}

					loopMP.stop();
					winMP.setMute(muted);
					winMP.play();
					winMP.setOnEndOfMedia(new Runnable() {

						@Override
						public void run() {
							window.setScene(gameOverScene(true));
						}

					});
				}
				if (game.guessesLeft == 0) {

					for (Button but : alphabetTemp) {
						but.setDisable(true);
					}

					loopMP.stop();
					loseMP.setMute(muted);
					loseMP.play();

					ghostTimeline.play();
					ghostFade.play();
					loseMP.setOnEndOfMedia(new Runnable() {

						@Override
						public void run() {
							window.setScene(gameOverScene(false));
						}

					});
				}
			});

			alphacount++;
		}
		alphaGrid.setAlignment(Pos.CENTER);
		alphaGrid.setPadding(new Insets(7));
		alphaGrid.setHgap(2);
		alphaGrid.setVgap(2);

		alphaGrid.getChildren().addAll(alphabet);
		content.getChildren().addAll(gamePane, alphaGrid);
		content.setBackground(new Background(new BackgroundFill(Color.rgb(140, 91, 0), null, null)));

		// add developer functionality for setting the phrase in game

		content.setOnKeyPressed(ex -> {
			if (ex.isAltDown() && ex.isControlDown() && ex.getCode() == KeyCode.SPACE) {
				openSetPhraseWindow();
			}
			if (ex.isAltDown() && ex.isControlDown() && ex.getCode() == KeyCode.W) {
				window.setScene(gameOverScene(true));
			}
			if (ex.isAltDown() && ex.isControlDown() && ex.getCode() == KeyCode.L) {
				window.setScene(gameOverScene(false));
			}
		});

		return new Scene(content);
	}

	private Scene gameOverScene(boolean win) {

		Pane content = new Pane();
		ImageView background = new ImageView();
		// create background
		if (win) {
			background = new ImageView(new Image(load("winBackground.png")));
		}
		if (!win) {
			background = new ImageView(new Image(load("loseBackground.png")));
		}
		background.setFitWidth(650);
		background.setFitHeight(510);
		// add buttons
		Text phraseText = new Text("The phrase was: " + game.gamePhrase.getPhrase());
		phraseText.setFont(Font.font(24));
		phraseText.setWrappingWidth(650);
		phraseText.setTextAlignment(TextAlignment.CENTER);
		phraseText.setY(190);

		// * new category
		ImageView newGameButton = new ImageView(new Image(load("playAgainButton.png")));
		newGameButton.setX(220);
		newGameButton.setY(200);
		newGameButton.setOnMousePressed(e -> {
			if (!muted) {
				click.seek(Duration.ZERO);
				click.play();
			}
			newGameButton.setEffect(pressEffect);
		});
		newGameButton.setOnMouseReleased(e -> {
			window.setScene(categoryScene());
		});

		// * same category
		ImageView sameGameButton = new ImageView(new Image(load("playCategoryAgainButton.png")));
		sameGameButton.setX(220);
		sameGameButton.setY(280);
		sameGameButton.setOnMousePressed(e -> {
			if (!muted) {
				click.seek(Duration.ZERO);
				click.play();
			}
			sameGameButton.setEffect(pressEffect);
		});
		sameGameButton.setOnMouseReleased(e -> {
			if (category == "TV") {
				window.setScene(gameScene(categories[0].getRandomPhrase()));
			}
			if (category == "MOVIES") {
				window.setScene(gameScene(categories[1].getRandomPhrase()));
			}
		});

		// * quit
		ImageView quitButton = new ImageView(new Image(load("quitButton.png")));
		quitButton.setX(220);
		quitButton.setY(360);
		quitButton.setOnMousePressed(e -> {
			if (!muted) {
				click.seek(Duration.ZERO);
				click.play();
			}
			quitButton.setEffect(pressEffect);
		});
		quitButton.setOnMouseReleased(e -> confQuit());
		// add button functionality

		content.getChildren().addAll(background, phraseText, newGameButton, sameGameButton, quitButton);
		return new Scene(content);
	}

	// developer functionality. will open on special key input on gameScene
	private void openSetPhraseWindow() {
		Stage phraseWindow = new Stage();
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setVgap(10);
		pane.setHgap(10);
		Label phraseLabel = new Label("Enter phrase:");
		GridPane.setConstraints(phraseLabel, 0, 0);
		TextField phraseField = new TextField(game.gamePhrase.getPhrase());
		GridPane.setConstraints(phraseField, 1, 0);

		Button setPhrase = new Button("Set Phrase");
		setPhrase.setOnAction(e -> {
			window.setScene(gameScene(new Phrase(phraseField.getText())));
			phraseWindow.close();
		});
		GridPane.setConstraints(setPhrase, 1, 1);

		pane.getChildren().addAll(phraseLabel, phraseField, setPhrase);

		Scene setPhraseScene = new Scene(pane);
		phraseWindow.setScene(setPhraseScene);
		phraseWindow.show();
	}

	private void confQuit() {

		Alert quit = new Alert(AlertType.CONFIRMATION);
		quit.setTitle("Confirm Selection");
		quit.setHeaderText("Are you sure you want to quit?");

		Button yesButton = (Button) quit.getDialogPane().lookupButton(ButtonType.OK);
		yesButton.setText("Yes");
		Button noButton = (Button) quit.getDialogPane().lookupButton(ButtonType.CANCEL);
		noButton.setText("No");
		
		Optional<ButtonType> choice = quit.showAndWait();
		if (choice.get() == ButtonType.OK) {
			window.close();
		}
		if (choice.get() == ButtonType.CANCEL) {
			quit.close();
		}
	}

	private InputStream load(String path) {
		InputStream input = getClass().getResourceAsStream(path);
		if (input == null) {
			input = getClass().getResourceAsStream("/" + path);
		}
		return input;
	}

	public static void main(String[] args) {
			launch(args);
	}

}