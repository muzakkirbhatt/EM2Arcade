package com.example.mbhatt1.em2arcade;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class BlackJackActivity extends AppCompatActivity

{
    private ArrayList<Cards> deck;
    private ArrayList<Cards> playerHand;
    private ArrayList<Cards> dealerHand;
    private Cards Card;
    private Context context;
    private DatabaseManager db;
    private ImageButton hitBtn;
    private ImageButton standBtn;
    private ImageView playerCard1;
    private ImageView playerCard2;
    private ImageView playerCard3;
    private ImageView playerCard4;
    private ImageView playerCard5;
    private ImageView playerCard6;
    private ImageView playerCard7;
    private ImageView playerCard8;
    private ImageView dealerCard1;
    private ImageView dealerCard2;
    private ImageView dealerCard3;
    private ImageView dealerCard4;
    private ImageView dealerCard5;
    private ImageView dealerCard6;
    private ImageView dealerCard7;
    private ImageView dealerCard8;
    private int playerTotal;
    private int dealerTotal;
    private int id;
    private int cardNum;
    private static int playerWins = 0;
    private static int dealerWins = 0;
    private User player;
    private TextView playerWinsText;
    private TextView dealerWinsText;
    private TextView playerTotalText;
    private TextView dealerTotalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack);

        db = new DatabaseManager(this);
        SharedPreferences sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        String username = sp.getString("userName", null);
        player = db.selectByUsername(username);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            playerWins = extras.getInt("playerWins");
            dealerWins = extras.getInt("dealerWins");
        }

        hitBtn = findViewById(R.id.hitBtn);
        standBtn = findViewById(R.id.standBtn);

        playerCard1 = findViewById(R.id.playerCard1);
        playerCard2 = findViewById(R.id.playerCard2);
        playerCard3 = findViewById(R.id.playerCard3);
        playerCard4 = findViewById(R.id.playerCard4);
        playerCard5 = findViewById(R.id.playerCard5);
        playerCard6 = findViewById(R.id.playerCard6);
        playerCard7 = findViewById(R.id.playerCard7);
        playerCard8 = findViewById(R.id.playerCard8);

        dealerCard1 = findViewById(R.id.dealerCard1);
        dealerCard2 = findViewById(R.id.dealerCard2);
        dealerCard3 = findViewById(R.id.dealerCard3);
        dealerCard4 = findViewById(R.id.dealerCard4);
        dealerCard5 = findViewById(R.id.dealerCard5);
        dealerCard6 = findViewById(R.id.dealerCard6);
        dealerCard7 = findViewById(R.id.dealerCard7);
        dealerCard8 = findViewById(R.id.dealerCard8);

        playerWinsText = findViewById(R.id.playerWins);
        dealerWinsText = findViewById(R.id.dealerWins);
        playerTotalText = findViewById(R.id.playerTotalText);
        dealerTotalText = findViewById(R.id.dealerTotalText);

        deck = new ArrayList<>();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();

        playerWinsText.setText("Player wins: " + playerWins);
        dealerWinsText.setText("Dealer wins: " + dealerWins);


        String suit[] = "hearts,spades,clubs,diamonds".split(",");
        String face[] = "ace,two,three,four,five,six,seven,eight,nine,ten,jack,queen,king".split(",");

        for (int suitNum = 0; suitNum < suit.length; suitNum++) {
            for (int faceNum = 0; faceNum < face.length; faceNum++) {
                Cards Card = new Cards();
                int value = 0;

                Card.setSuit(suit[suitNum]);
                Card.setFace(face[faceNum]);
                Card.setFileName("@drawable/" + face[faceNum] + "_" + suit[suitNum]);

                if (String.valueOf(face[faceNum]).matches("two")) {
                    value = 2;
                }

                if (String.valueOf(face[faceNum]).matches("three")) {
                    value = 3;
                }

                if (String.valueOf(face[faceNum]).matches("four")) {
                    value = 4;
                }

                if (String.valueOf(face[faceNum]).matches("five")) {
                    value = 5;
                }

                if (String.valueOf(face[faceNum]).matches("six")) {
                    value = 6;
                }

                if (String.valueOf(face[faceNum]).matches("seven")) {
                    value = 7;
                }

                if (String.valueOf(face[faceNum]).matches("eight")) {
                    value = 8;
                }

                if (String.valueOf(face[faceNum]).matches("nine")) {
                    value = 9;
                }

                if (String.valueOf(face[faceNum]).matches("ten|jack|queen|king")) {
                    value = 10;
                }
                if (String.valueOf(face[faceNum]).equals("ace")) {
                    value = 11;
                    Card.setAceValue(1);
                }
                Card.setValue(value);

                deck.add(Card);
            }
        }

        Collections.shuffle(deck);

        cardNum = 0;
        playerTotal = 0;
        dealerTotal = 0;

        Card = deck.get(cardNum);
        playerHand.add(Card);
        playerTotal += Card.getValue();
        context = playerCard1.getContext();
        id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
        playerCard1.setImageResource(id);
        cardNum++;

        Card = deck.get(cardNum);
        dealerHand.add(Card);
        dealerTotal += Card.getValue();
        context = dealerCard1.getContext();
        id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
        dealerCard1.setImageResource(id);
        cardNum++;

        Card = deck.get(cardNum);
        playerHand.add(Card);
        playerTotal += Card.getValue();
        context = playerCard2.getContext();
        id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
        playerCard2.setImageResource(id);
        cardNum++;

        Card = deck.get(cardNum);
        dealerHand.add(Card);

        if (Integer.valueOf(dealerTotal + Card.getValue()) == 21) {
            dealerTotal += Card.getValue();
            context = dealerCard2.getContext();
            id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
            dealerCard2.setImageResource(id);
            dealerTotalText.setText(String.valueOf(dealerTotal));

            showGameDialog("dealer");

        } else {
            dealerCard2.setImageResource(R.drawable.backcardblue);
        }
        cardNum++;

        playerTotalText.setText(String.valueOf(playerTotal));
        dealerTotalText.setText(String.valueOf(dealerTotal));

        check();

        hitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card = deck.get(cardNum);
                playerHand.add(Card);
                playerTotal += Card.getValue();
                playerTotalText.setText(String.valueOf(playerTotal));

                if (playerCard3.getDrawable() == null) {
                    context = playerCard3.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    playerCard3.setImageResource(id);
                    cardNum++;
                } else if (playerCard4.getDrawable() == null) {
                    context = playerCard4.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    playerCard4.setImageResource(id);
                    cardNum++;
                } else if (playerCard5.getDrawable() == null) {
                    context = playerCard5.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    playerCard5.setImageResource(id);
                    cardNum++;
                } else if (playerCard6.getDrawable() == null) {
                    context = playerCard6.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    playerCard6.setImageResource(id);
                    cardNum++;
                } else if (playerCard7.getDrawable() == null) {
                    context = playerCard7.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    playerCard7.setImageResource(id);
                    cardNum++;
                } else if (playerCard8.getDrawable() == null) {
                    context = playerCard8.getContext();
                    id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                    playerCard8.setImageResource(id);
                    cardNum++;
                }

                check();
            }
        });

        standBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card = dealerHand.get(1);
                dealerTotal += Card.getValue();
                dealerTotalText.setText(String.valueOf(dealerTotal));
                context = dealerCard2.getContext();
                id = context.getResources().getIdentifier(Card.getFileName(), "drawable", context.getPackageName());
                dealerCard2.setImageResource(id);

                while (checkDealer()) {
                    Card = deck.get(cardNum);
                    dealerHand.add(Card);
                    dealerTotal += Card.getValue();
                    cardNum++;
                }

                if (dealerHand.size() >= 3) {
                    context = dealerCard3.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(2).getFileName(), "drawable", context.getPackageName());
                    dealerCard3.setImageResource(id);
                    dealerTotalText.setText(String.valueOf(dealerTotal));
                }

                if (dealerHand.size() >= 4) {
                    context = dealerCard4.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(3).getFileName(), "drawable", context.getPackageName());
                    dealerCard4.setImageResource(id);
                    dealerTotalText.setText(String.valueOf(dealerTotal));
                }

                if (dealerHand.size() > 5) {
                    context = dealerCard5.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(4).getFileName(), "drawable", context.getPackageName());
                    dealerCard5.setImageResource(id);
                    dealerTotalText.setText(String.valueOf(dealerTotal));
                }

                if (dealerHand.size() > 6) {
                    context = dealerCard6.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(5).getFileName(), "drawable", context.getPackageName());
                    dealerCard6.setImageResource(id);
                    dealerTotalText.setText(String.valueOf(dealerTotal));
                }

                if (dealerHand.size() >= 7) {
                    context = dealerCard7.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(6).getFileName(), "drawable", context.getPackageName());
                    dealerCard7.setImageResource(id);
                    dealerTotalText.setText(String.valueOf(dealerTotal));
                }

                if (dealerHand.size() >= 8) {
                    context = dealerCard8.getContext();
                    id = context.getResources().getIdentifier(dealerHand.get(7).getFileName(), "drawable", context.getPackageName());
                    dealerCard8.setImageResource(id);
                    dealerTotalText.setText(String.valueOf(dealerTotal));
                }
            }
        });

        db.close();
    }


    boolean checkDealer() {

        if (dealerTotal > 21) {

            for (int s = 0; s < dealerHand.size(); s++) {
                Cards tempCard = dealerHand.get(s);
                if (tempCard.getFace().equals("ace")) {
                    tempCard.setValue(tempCard.getAceValue());
                    dealerTotal = 0;
                    for (int w = 0; w < dealerHand.size(); w++) {
                        dealerTotal += dealerHand.get(w).getValue();
                    }

                    dealerTotalText.setText(String.valueOf(dealerTotal));
                    return true;
                }
            }

            showGameDialog("player");
        } else if (dealerTotal == 21 && playerTotal == 21) {
            showGameDialog("both");
        } else if (dealerTotal > 17) {

            if (playerTotal > dealerTotal) {
                showGameDialog("player");
            } else if (dealerTotal > playerTotal) {
                showGameDialog("dealer");
            }
        } else if (dealerTotal < 17) {
            return true;
        }

        return false;
    }

    void check() {
        if (playerTotal > 21) {
            for (int s = 0; s < playerHand.size(); s++) {
                Cards tempCard = playerHand.get(s);
                if (tempCard.getFace().equals("ace")) {
                    tempCard.setValue(tempCard.getAceValue());
                    playerTotal = 0;
                    for (int w = 0; w < playerHand.size(); w++) {
                        playerTotal += playerHand.get(w).getValue();
                    }

                    playerTotalText.setText(String.valueOf(playerTotal));

                    if (s == (playerHand.size() - 1)) {
                        return;
                    }
                }
            }

            showGameDialog("dealer");
        }

        if (playerTotal == 21) {
            showGameDialog("player");
        }
    }

    public void showGameDialog(String winner) {
        String title;
        if (winner.equalsIgnoreCase("dealer")) {
            title = "TOUGH LUCK! YOU LOST";
            dealerWins++;
        } else if (winner.equalsIgnoreCase("player")) {
            title = "WOOO! YOU WIN";
            playerWins++;
            int wins = Integer.valueOf(player.getBlackjackHS()) + 1;
            player.setBlackjackHS(String.valueOf(wins));
            db.updateHS(player, "blackJack");
        } else {
            title = "TIE";
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage("That was fun! Do you want to play again?");
        DialogMaker playAgain = new DialogMaker();
        alert.setPositiveButton("Yes", playAgain);
        alert.setNegativeButton("No, Thanks!", playAgain);
        alert.setCancelable(false);
        setFinishOnTouchOutside(false);
        alert.show();
    }

    private class DialogMaker implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            if (id == -1) /* YES button */ {
                Intent intent = getIntent();
                intent.putExtra("playerWins", playerWins);
                intent.putExtra("dealerWins", dealerWins);
                BlackJackActivity.this.finish();
                startActivity(intent);

            } else if (id == -2) // NO button
                BlackJackActivity.this.finish();
        }
    }
}
