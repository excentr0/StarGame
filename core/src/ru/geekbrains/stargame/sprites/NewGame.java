package ru.geekbrains.stargame.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.screen.GameScreen;

public class NewGame extends Sprite {

  private static final float TEXT_SCALE = 0.9f;

  private final Game game;

  private int     pointer;
  private boolean pressed;

  public NewGame(final TextureAtlas atlas,
                 Game game) throws GameException {
    super(atlas.findRegion("button_new_game"));
    this.game = game;
  }

  @Override
  public void resize(final Rect worldBounds) {
    setHeightProportion(0.07f);
    setTop(-0.1f);
  }

  @Override
  public boolean touchDown(final Vector2 touch,
                           final int pointer,
                           final int button) {
    if (pressed || !isMe(touch)) {
      return false;
    }
    this.pointer = pointer;
    scale        = TEXT_SCALE;
    pressed      = true;
    return true;
  }

  @Override
  public boolean touchUp(final Vector2 touch,
                         final int pointer,
                         final int button) {
    if (this.pointer != pointer || !pressed) {
      return false;
    }
    if (isMe(touch)) {
      action();
    }
    pressed = false;
    scale   = 1f;
    return true;
  }

  private void action() {
    game.setScreen(new GameScreen(game));
  }
}
