package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.sprites.BackgroundSprite;
import ru.geekbrains.stargame.sprites.ShipSprite;

public class GameScreen extends BaseScreen {

  private Texture background;
  private Texture smallShip;
  private ShipSprite shipSprite;
  private BackgroundSprite backgroundSprite;

  @Override
  public void show() {
    super.show();
    background = new Texture("space.png");
    smallShip = new Texture("small_ship.png");
    try {
      shipSprite = new ShipSprite(smallShip);
      backgroundSprite = new BackgroundSprite(background);
    } catch (GameException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void render(float delta) {
    shipSprite.update(delta);
    draw();
  }

  @Override
  public void dispose() {
    batch.dispose();
    background.dispose();
    smallShip.dispose();
    super.dispose();
  }

  @Override
  public boolean touchDown(
      final int screenX, final int screenY, final int pointer, final int button) {
    calcTouch(screenX, screenY);
    shipSprite.calcNewPosition(touch);
    return false;
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    backgroundSprite.draw(batch);
    shipSprite.draw(batch);
    batch.end();
  }
}
