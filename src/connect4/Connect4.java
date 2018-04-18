package connect4;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author AlexiaDura
 */
public class Connect4 extends Application {

    //VARIABLES
    //===========================================
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private boolean playerTurn = true;
    private Disk[][] board;
    private StackPane panel, panel2, panel3;
    private BorderPane borderPane;
    private VBox box1, box2, box3, footer;
    private HBox buttons;
    private GridPane gridpane;
    private Circle circles[][];
    private Pane diskPlaced;
    private int posRow[] = new int[7];
    private List<Point2D> vertical, horizontal, diagonal1, diagonal2;
    private Point2D topLeft, botLeft;
    private Text title, title2, title3, title4, instruct, cite;
    private Button startButton, instrucButton, homeButton, exitButton;
    private Scene scene1, scene2, scene3;
    final private String instructions = "Insert a disk in one of the rows and try to connect 4 of your own coloured disks! \nYou can connect 4 diagonally, horizontally or vertically. \nIf you connect 4 before your opponent, YOU WIN!";
    final private BackgroundImage myBI = new BackgroundImage(new Image("back3.jpg", 1100, 760, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
    final private BackgroundImage myBI2 = new BackgroundImage(new Image("back3.jpg", 800, 600, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

    //START FIRST STAGE
    //===========================================================
    @Override
    public void start(Stage primaryStage) {
        //Main Panel with background image
        panel = new StackPane();
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(30));
        panel.setBackground(new Background(myBI2));

        //vbox for buttons and titles in list mode
        box1 = new VBox();
        box1.setStyle("-fx-background-color: rgba(0, 0, 0, .5)");
        box1.setAlignment(Pos.CENTER);
        box1.setSpacing(40);

        //Title and subtitle
        title = new Text("Connect4 Game");
        title2 = new Text("Welcome!");
        title.setFont(Font.font("Tahoma", FontWeight.LIGHT, 38));
        title2.setFont(Font.font("Verdana", FontWeight.EXTRA_LIGHT, 28));
        title.setStyle("-fx-fill: #fff");
        title2.setStyle("-fx-fill: #fff");

        //Buttons for start and instuctions
        startButton = new Button();
        instrucButton = new Button();

        //cite for footer
        cite = new Text("By Alexia C. Durá");
        cite.setFont(Font.font("Verdana", FontWeight.EXTRA_LIGHT, 18));
        cite.setStyle("-fx-fill: #fff");

        //listener for button instructions
        instrucButton.setText("Instructions");
        instrucButton.setOnAction(e -> {
            viewInstructions(primaryStage, false, "");
            primaryStage.setScene(scene2);
        });

        //listener for button start game
        startButton.setText("Start Game");
        startButton.setOnAction(e -> {
            startGame(primaryStage);
            primaryStage.setScene(scene3);
        });

        //Add to vbox
        box1.getChildren().add(title);
        box1.getChildren().add(title2);
        box1.getChildren().add(instrucButton);
        box1.getChildren().add(startButton);
        box1.getChildren().add(cite);
        //add to main panel
        panel.getChildren().add(box1);

        //set first scene
        scene1 = new Scene(panel, 800, 600);

        //set primary stage
        primaryStage.setTitle("4 in a Row");
        primaryStage.setScene(scene1);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void viewInstructions(Stage primaryStage, boolean win, String winner) {
        //Main Panel with background image
        panel2 = new StackPane();
        panel2.setAlignment(Pos.CENTER);
        panel2.setPadding(new Insets(30));
        panel2.setBackground(new Background(myBI2));

        //vbox for instructions in list mode center
        box2 = new VBox();
        box2.setStyle("-fx-background-color: rgba(0, 0, 0, .6);");
        box2.setAlignment(Pos.CENTER);
        box2.setSpacing(30);

        //Buttons for starting game and returning to home
        homeButton = new Button();
        startButton.setText("New Game");

        if (win) {
            homeButton.setText("Exit");
            title3 = new Text(winner);
            title4 = new Text("Play again?");
            //listener for returning home
            homeButton.setOnAction(e -> {
                System.exit(0);
            });

            //Add to box
            box2.getChildren().add(title3);
            box2.getChildren().add(title4);
        } else {
            //Title and instructions
            homeButton.setText("Home");
            title3 = new Text("Instructions");
            title4 = new Text("Your first time? The game is very simple :-)");
            instruct = new Text(instructions);
            instruct.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
            instruct.setStyle("-fx-fill: #fff");

            //listener for returning home
            homeButton.setOnAction(e -> {
                start(primaryStage);
                primaryStage.setScene(scene1);
            });
            //Add to box
            box2.getChildren().add(title3);
            box2.getChildren().add(title4);
            box2.getChildren().add(instruct);
        }

        //set fonts to text
        title3.setFont(Font.font("Tahoma", FontWeight.LIGHT, 48));
        title4.setFont(Font.font("Tahoma", FontWeight.LIGHT, 27));

        //set style to text
        title3.setStyle("-fx-fill: #fff");
        title4.setStyle("-fx-fill: #fff");

        //cite for footer
        cite = new Text("By Alexia C. Durá");
        cite.setFont(Font.font("Verdana", FontWeight.EXTRA_LIGHT, 18));
        cite.setStyle("-fx-fill: #fff");

        //listener for starting game (choosing player first)
        startButton.setOnAction(e -> {
            startGame(primaryStage);
            primaryStage.setScene(scene3);
        });

        box2.getChildren().add(startButton);
        box2.getChildren().add(homeButton);
        box2.getChildren().add(cite);
        //add to main oanel with background image
        panel2.getChildren().add(box2);

        //set second scene
        scene2 = new Scene(panel2, 800, 600);

        //set primary stage with scene 2
        primaryStage.setTitle("4 in a Row");
        primaryStage.setScene(scene2);
        primaryStage.show();
    }

    //START GAME
    //============================================
    public void startGame(Stage primaryStage) {
        //main panel with backgroung image
        borderPane = new BorderPane();
        //initialize circle matriz
        circles = new Circle[7][6];
        //initialize board with places disks
        diskPlaced = new Pane();
        //initialize points to null
        vertical = null;
        horizontal = null;
        diagonal1 = null;
        diagonal2 = null;
        topLeft = null;
        botLeft = null;
        
        //initialize board with columns and rows
        board = new Disk[COLUMNS][ROWS];

        //panel for gamelayout (board)
        panel3 = new StackPane();
        panel3.setAlignment(Pos.CENTER_LEFT);
        panel3.setPadding(new Insets(28));
        borderPane.setBackground(new Background(myBI));

        //box for top insert buttons
        buttons = new HBox();
        buttons.setSpacing(55);
        buttons.setPadding(new Insets(30));
        buttons.setAlignment(Pos.CENTER_LEFT);

        //controller for exit game
        exitButton = new Button();
        exitButton.setText("Home");
        exitButton.setOnAction(e -> {
            start(primaryStage);
            primaryStage.setScene(scene1);
        });

        //cite for footer
        cite = new Text("By Alexia C. Durá");
        cite.setFont(Font.font("Verdana", FontWeight.EXTRA_LIGHT, 18));
        cite.setStyle("-fx-fill: #fff");

        //vboxfor gridlayout
        box3 = new VBox();
        box3.setAlignment(Pos.CENTER_LEFT);

        //vboxfor footer
        footer = new VBox();
        footer.setAlignment(Pos.BOTTOM_CENTER);

        //scene for game
        scene3 = new Scene(borderPane, 740, 740);

        //gridpane for game layout with rings
        gridpane = new GridPane();
        gridpane.setPadding(new Insets(8));
        gridpane.setHgap(11);
        gridpane.setVgap(11);
        gridpane.setAlignment(Pos.CENTER_LEFT);

        //creat lighting effect when column is selected
        Light.Distant lightfx = new Light.Distant();
        lightfx.setAzimuth(85.0);
        lightfx.setElevation(80.0);

        Lighting lightfx2 = new Lighting();
        lightfx2.setLight(lightfx);
        lightfx2.setSurfaceScale(8.0);

        //for to create circles and insert buttons for layout
        for (int i = 0; i < 7; i++) {
            Button columnsButtons = new Button();
            columnsButtons.setText("insert");
            columnsButtons.setId(String.valueOf(i));
            columnsButtons.setEffect(lightfx2);
            columnsButtons.setOnAction(e -> {
                placeDisk(new Disk(playerTurn), columnsButtons, primaryStage);
            });
            buttons.getChildren().add(columnsButtons);
            for (int j = 0; j < 6; j++) {
                circles[i][j] = new Circle(42);
                circles[i][j].setFill(Color.WHITE);
                gridpane.add(circles[i][j], i, j);
            }

        }

        //set row position to bottom for decrement when disk is placed
        for (int j = 0; j < posRow.length; j++) {
            posRow[j] = 5;
        }

        //add gridpane to box3
        box3.getChildren().add(gridpane);
        box3.setEffect(lightfx2);
        //add box to panel
        panel3.getChildren().add(box3);
        panel3.getChildren().add(diskPlaced);

        //add buttons and center panel to main panel with background image
        borderPane.setTop(buttons);
        borderPane.setCenter(panel3);
        footer.getChildren().add(cite);
        borderPane.setBottom(footer);

        //set scene 4
        primaryStage.setScene(scene3);
        primaryStage.show();
    }

    //function for placing disk in board
    private void placeDisk(Disk disk, Button button, Stage primaryStage) {
        //parse id button
        int column = Integer.parseInt(button.getId());
        //start from columns -1 
        int row = ROWS - 1;
        //do while for placing and calculating range and if no disk is placed
        do {
            if (!getDisk(column, row).isPresent()) {
                break;
            }
            row--;
        } while (row >= 0);

        //if out of range no action
        if (row < 0) {
            return;
        }

        //creat lighting effect when column is selected
        Light.Distant lightfx = new Light.Distant();
        lightfx.setAzimuth(85.0);
        lightfx.setElevation(80.0);

        Lighting lightfx2 = new Lighting();
        lightfx2.setLight(lightfx);
        lightfx2.setSurfaceScale(8.0);

        //board were disk is placed
        board[column][row] = disk;
        //set effect to board
        board[column][row].setEffect(lightfx2);
        //add disk
        diskPlaced.getChildren().add(disk);
        //set translate to columns
        disk.setTranslateX(column * (43 + 52) + 43 / 6);

        //set current row
        final int currentRow = row;

        //set animation before placing disk
        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disk);
        animation.setToY(row * (43 + 52.5) + 43 / 7);
        animation.setOnFinished(e -> {
            if (calculateResult(column, currentRow)) {
                finishGame(primaryStage);
            }
            //switch player
            playerTurn = !playerTurn;

        });
        //play animation
        animation.play();
    }

    //calculate results with  Point 2d object
    private boolean calculateResult(int column, int row) {
        //vertical and horizontal check
        vertical = IntStream.rangeClosed(row - 3, row + 3).mapToObj(r -> new Point2D(column, r)).collect(Collectors.toList());
        horizontal = IntStream.rangeClosed(column - 3, column + 3).mapToObj(c -> new Point2D(c, row)).collect(Collectors.toList());

        //diagonal one check
        topLeft = new Point2D(column - 3, row - 3);
        diagonal1 = IntStream.rangeClosed(0, 6).mapToObj(i -> topLeft.add(i, i)).collect(Collectors.toList());

        //diagonal2 check
        botLeft = new Point2D(column - 3, row + 3);
        diagonal2 = IntStream.rangeClosed(0, 6).mapToObj(i -> botLeft.add(i, -i)).collect(Collectors.toList());

        //return to control and check range
        return controlBoard(vertical) || controlBoard(horizontal) || controlBoard(diagonal1) || controlBoard(diagonal2);

    }

    //functiont o control board
    private boolean controlBoard(List<Point2D> points) {
        //count valid disks connected
        int valid = 0;
        //for each to point 2d array
        for (Point2D point : points) {
            //store column
            int column = (int) point.getX();
            //store row
            int row = (int) point.getY();

            //create new disk calling getdisk to obtain player
            Disk disk = getDisk(column, row).orElse(new Disk(!playerTurn));
            //if player count valids
            if (disk.red == playerTurn) {
                valid++;
                //if 4 connected return win
                if (valid == 4) {
                    return true;
                }
                //if not set valid=0;
            } else {
                valid = 0;
            }
            //return not win
        }
        return false;
    }

    //function to check if disk is correclty placed
    private Optional<Disk> getDisk(int column, int row) {
        if (column < 0 || column >= COLUMNS || row < 0 || row >= ROWS) {
            return Optional.empty();
        }
        return Optional.ofNullable(board[column][row]);

    }

    //change scene when game is finished and show winner
    private void finishGame(Stage primaryStage) {
        if (playerTurn) {
            viewInstructions(primaryStage, true, "BLUE IS A WINNER!");
            primaryStage.setScene(scene2);
        } else {
            viewInstructions(primaryStage, true, "RED IS A WINNER!");
            primaryStage.setScene(scene2);
        }
    }

    //launch app
    public static void main(String[] args) {
        launch(args);
    }

}
