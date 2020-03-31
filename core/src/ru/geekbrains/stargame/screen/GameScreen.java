package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprites.BackgroundSprite;
import ru.geekbrains.stargame.sprites.ShipSprite;

public class GameScreen extends BaseScreen {

  private TextureRegion background;
  private TextureRegion smallShip;
  private ShipSprite shipSprite;
  private BackgroundSprite backgroundSprite;
  private TextureAtlas atlas;

  @Override
  public void show() {
    super.show();
    atlas = new TextureAtlas("StarGame.pack");
    background = atlas.findRegion("background");
    smallShip = atlas.findRegion("ship");
    try {
      shipSprite = new ShipSprite(smallShip);
      backgroundSprite = new BackgroundSprite(background);
    } catch (GameException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void render(float delta) {
    shipSprite.update(delta);
    draw();
  }

  @Override
  public void resize(final Rect worldBounds) {
    shipSprite.resize(worldBounds);
    backgroundSprite.resize(worldBounds);
  }

  @Override
  public void dispose() {
    batch.dispose();
    super.dispose();
  }

  @Override
  public boolean touchDown(final Vector2 touch, final int pointer, final int button) {
    shipSprite.touchDown(touch, pointer, button);
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
