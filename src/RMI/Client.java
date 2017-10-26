/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Game.Board;
import Game.Piece;
import Game.Player;
import java.awt.Color;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import ludusbraille.LudusBrailleGame;

/**
 *
 * @author guilhermecosta
 */
public class Client extends UnicastRemoteObject implements ClientMethods {

    private static final long serialVersionUID = 1L;
    public Piece pieceType = new Piece(9, 9, true);
    public Board board = new Board();
    public LudusBrailleGame game;
    public ServerMethods server;
    public Player player;
    public int circlePiece, crownPiece;
    public boolean isDisable = false;
    public boolean isTurn = false;
    public Registry registry;

    public Client() throws RemoteException, NotBoundException {
        String serverName = JOptionPane.showInputDialog(null, "Bem vindo Cliente! Forneca o nome do servidor registrado:");
        String port = JOptionPane.showInputDialog(null, "Digite a porta do servidor:");
        int portNumber = Integer.parseInt(port);
        
        System.out.println("Obtendo registro de cliente...");
        registry = LocateRegistry.getRegistry(portNumber);
        server = (ServerMethods) registry.lookup(serverName);
        game = new LudusBrailleGame();
        game.client = this;
        game.registry();
        game.setVisible(true);
        board.initBoard();
        enableToPlay(false);
        initPieces();
        System.out.println("Cliente registrado!");
    }
    
    
    //Local Methods
    public void setTypePieceClient(int type) {
        pieceType.setTypePiece(type);
    }

    public int getTypePieceClient() {
        return pieceType.getTypePiece();
    }

    public void initPieces() {
        game.labelCrownPiece.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Elipse.png")));
        game.labelCirclePiece.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Coroa.png")));
        circlePiece = pieceType.getCirclePiece();
        crownPiece = pieceType.getCrownPiece();
    }

    public void updatePieceSlot(int row, int column, int type) {
        JLabel labelPos = setLabelPosition(row, column);
        if (type == 0) {
            labelPos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CelulaVazia.png")));
        }
        if (type == 1) {
            circlePiece--;
            game.piece1.setText(String.valueOf(circlePiece));
            labelPos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Coroa.png")));
        }
        if (type == 2) {
            crownPiece--;
            game.piece2.setText(String.valueOf(crownPiece));
            labelPos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Elipse.png")));
        }
        if (type == 3) {
            labelPos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CoroaElipse.png")));
        }
    }

    public JLabel setLabelPosition(int labelRow, int labelColumn) {
        JLabel labelPosition = new JLabel();

        if (labelRow == 0 && labelColumn == 0) {
            labelPosition = game.labelCell00;
        }
        if (labelRow == 0 && labelColumn == 1) {
            labelPosition = game.labelCell01;
        }
        if (labelRow == 0 && labelColumn == 2) {
            labelPosition = game.labelCell02;
        }
        if (labelRow == 0 && labelColumn == 3) {
            labelPosition = game.labelCell03;
        }
        if (labelRow == 1 && labelColumn == 0) {
            labelPosition = game.labelCell10;
        }
        if (labelRow == 1 && labelColumn == 1) {
            labelPosition = game.labelCell11;
        }
        if (labelRow == 1 && labelColumn == 2) {
            labelPosition = game.labelCell12;
        }
        if (labelRow == 1 && labelColumn == 3) {
            labelPosition = game.labelCell13;
        }
        if (labelRow == 2 && labelColumn == 0) {
            labelPosition = game.labelCell20;
        }
        if (labelRow == 2 && labelColumn == 1) {
            labelPosition = game.labelCell21;
        }
        if (labelRow == 2 && labelColumn == 2) {
            labelPosition = game.labelCell22;
        }
        if (labelRow == 2 && labelColumn == 3) {
            labelPosition = game.labelCell23;
        }
        return labelPosition;
    }

    public void setLabelData(int row, int column, int typeCell) {
        if (circlePiece < 1 || crownPiece < 1) {
            game.logArea.append(game.playerName + ", voce não tem mais dessa peça para jogar!\n");
            game.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
        } else {
            if (typeCell == 0) {
                game.logArea.append(game.playerName + ", selecione uma peça para jogar!\n");
                game.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
                board.boardTable[row][column] = 0;
            }
            if (typeCell == 1) {
                updatePieceSlot(row, column, typeCell);
                board.boardTable[row][column] = 1;
            }
            if (typeCell == 2) {
                updatePieceSlot(row, column, typeCell);
                board.boardTable[row][column] = 2;
            }
            if (typeCell == 3) {
                updatePieceSlot(row, column, typeCell);
                board.boardTable[row][column] = 3;
            }
        }
        pieceType.setTypePiece(0);
    }

    public void checkAvaibleCell() {
        //check if this cell is avaible to set pieces...
    }

    public void enableToPlay(boolean answer) {
        isDisable = answer;
        if (isDisable) {
            game.circlePieceBlock.setBackground(Color.GREEN);
            game.crownPieceBlock.setBackground(Color.GREEN);

            game.circlePieceBlock.setVisible(answer);
            game.crownPieceBlock.setVisible(answer);

            game.labelCirclePiece.setVisible(answer);
            game.labelCrownPiece.setVisible(answer);

            game.piece1.setVisible(answer);
            game.piece2.setVisible(answer);
        } else {
            game.circlePieceBlock.setBackground(Color.RED);
            game.crownPieceBlock.setBackground(Color.RED);

            game.circlePieceBlock.setVisible(answer);
            game.crownPieceBlock.setVisible(answer);

            game.labelCirclePiece.setVisible(answer);
            game.labelCrownPiece.setVisible(answer);

            game.piece1.setVisible(answer);
            game.piece2.setVisible(answer);
        }
    }

    //Remote Methods
    @Override
    public void move(int row, int column, int typeCell) throws RemoteException {
        LudusBrailleGame.logArea.append("Peça lida no protocolo: " + typeCell + "\n");
        LudusBrailleGame.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
        setLabelData(row, column, typeCell);
        game.isYourTurn(true);
    }

    @Override
    public void chatTalk(String msg) throws RemoteException {
        if (!msg.equals("")) {
            LudusBrailleGame.chatArea.append(this.game.playerName + ": " + msg + "\n");
            LudusBrailleGame.chatArea.setCaretPosition(LudusBrailleGame.chatArea.getDocument().getLength());
            game.sendText.setText("");
        }
    }

    @Override
    public void start(int action) throws RemoteException {
        if (action == 1) {
            enableToPlay(true);
        } else {
            action = 0;
            enableToPlay(false);
        }
        game.logArea.append("Partida iniciada, boa sorte aos jogadores!\n");
        game.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
    }

    @Override
    public void restart(int action) throws RemoteException {
        int type = 0;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                updatePieceSlot(row, column, type);
            }
        }
        pieceType.resetPieces();
        circlePiece = pieceType.getCirclePiece();
        crownPiece = pieceType.getCrownPiece();
        game.piece1.setText(String.valueOf(circlePiece));
        game.piece2.setText(String.valueOf(crownPiece));

        if (action == 1) {
            enableToPlay(true);
            start(action);
            game.logArea.append("Partida reiniciada! Boa sorte!\n\n");
            game.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
        }
    }

    @Override
    public void surrender(int action) throws RemoteException {
        int type = 0;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 4; column++) {
                updatePieceSlot(row, column, type);
            }
        }
        pieceType.resetPieces();
        circlePiece = pieceType.getCirclePiece();
        crownPiece = pieceType.getCrownPiece();
        game.piece1.setText(String.valueOf(circlePiece));
        game.piece2.setText(String.valueOf(crownPiece));

        if (action == 1) {
            game.logArea.setText("\n");
            game.logArea.setText("Você desistiu do jogo! Oponente venceu! Inicie uma nova partida! Não desista!\n");
            game.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
        }
    }

    @Override
    public void turn(boolean turn) throws RemoteException {
        //implementado localmente
    }

    @Override
    public void enableGame(boolean isDisable) throws RemoteException {
        enableToPlay(isDisable);
    }
    
    public static void main(String[] args) {
        try {
            Client client = new Client();
        } catch (NotBoundException | RemoteException e) {
        }
    }
}
