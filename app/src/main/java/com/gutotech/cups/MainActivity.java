package com.gutotech.cups;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.tapadoo.alerter.Alerter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView left, middle, right;

    private List<Integer> cards;
    private static final int ACE_OF_SPADES = 107;
    private static final int SEVEN_OF_HEARTS = 207;
    private static final int SEVEN_OF_DIAMONDS = 407;

    private enum Direction {LEFT, MIDDLE, RIGHT}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        left = findViewById(R.id.left);
        middle = findViewById(R.id.middle);
        right = findViewById(R.id.right);
        Button new_game = findViewById(R.id.new_game);

        cards = new LinkedList<>();
        cards.add(ACE_OF_SPADES);
        cards.add(SEVEN_OF_HEARTS);
        cards.add(SEVEN_OF_DIAMONDS);

        shuffle();

        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffle();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignImages(Direction.LEFT);
            }
        });

        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignImages(Direction.MIDDLE);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignImages(Direction.RIGHT);
            }
        });
    }

    public void shuffle() {
        Collections.shuffle(cards);

        left.setImageResource(R.drawable.ic_cardback);
        middle.setImageResource(R.drawable.ic_cardback);
        right.setImageResource(R.drawable.ic_cardback);

        Animation anim_left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left);
        Animation anim_middle = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.middle);
        Animation anim_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right);

        anim_left.setAnimationListener(animationListener);
        anim_middle.setAnimationListener(animationListener);
        anim_right.setAnimationListener(animationListener);

        left.startAnimation(anim_left);
        middle.startAnimation(anim_middle);
        right.startAnimation(anim_right);
    }

    private final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            enableImages(false);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            enableImages(true);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    public void assignImages(Direction chosenCard) {
        enableImages(false);

        Direction winnerCard = Direction.LEFT;

        if (cards.get(0) == ACE_OF_SPADES) {
            left.setImageResource(R.drawable.ic_spades);
            winnerCard = Direction.LEFT;
        } else if (cards.get(0) == SEVEN_OF_HEARTS)
            left.setImageResource(R.drawable.ic_hearts);
        else if (cards.get(0) == SEVEN_OF_DIAMONDS)
            left.setImageResource(R.drawable.ic_diamonds);

        if (cards.get(1) == ACE_OF_SPADES) {
            middle.setImageResource(R.drawable.ic_spades);
            winnerCard = Direction.MIDDLE;
        } else if (cards.get(1) == SEVEN_OF_HEARTS)
            middle.setImageResource(R.drawable.ic_hearts);
        else if (cards.get(1) == SEVEN_OF_DIAMONDS)
            middle.setImageResource(R.drawable.ic_diamonds);

        if (cards.get(2) == ACE_OF_SPADES) {
            right.setImageResource(R.drawable.ic_spades);
            winnerCard = Direction.RIGHT;
        } else if (cards.get(2) == SEVEN_OF_HEARTS)
            right.setImageResource(R.drawable.ic_hearts);
        else if (cards.get(2) == SEVEN_OF_DIAMONDS)
            right.setImageResource(R.drawable.ic_diamonds);

        if (chosenCard == winnerCard)
            Alerter.create(this)
                    .setTitle("Guessed!")
                    .setIcon(R.drawable.ic_sentiment_satisfied_24dp)
                    .setBackgroundColor(android.R.color.holo_green_light)
                    .show();
    }

    private void enableImages(boolean enable) {
        left.setEnabled(enable);
        middle.setEnabled(enable);
        right.setEnabled(enable);
    }
}
