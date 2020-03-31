package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprites.AsteroidSprite;
import ru.geekbrains.stargame.sprites.BackgroundSprite;
import ru.geekbrains.stargame.sprites.ShipSprite;

public class GameScreen extends BaseScreen {
  private static final int ASTEROID_COUNT = 64;

  private ShipSprite shipSprite;
  private BackgroundSprite backgroundSprite;

  private AsteroidSprite[] asteroids;

  @Override
  public void show() {
    super.show();

    final TextureAtlas gameAtlas = new TextureAtlas("StarGame.pack");
    final TextureRegion background = gameAtlas.findRegion("background");
    final TextureRegion smallShip = gameAtlas.findRegion("ship");

    try {
      shipSprite = new ShipSprite(smallShip);
      backgroundSprite = new BackgroundSprite(background);
      asteroids = new AsteroidSprite[64];
      for (int i = 0; i < ASTEROID_COUNT; i++) {
        asteroids[i] = new AsteroidSprite(gameAtlas);
      }
    } catch (GameException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void render(float delta) {
    shipSprite.update(delta);
    update(delta);
    draw();
  }

  private void update(final float delta) {
    for (AsteroidSprite asteroidSprite : asteroids) {
      asteroidSprite.update(delta);
    }
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    backgroundSprite.draw(batch);
    for (AsteroidSprite asteroidSprite : asteroids) {
      asteroidSprite.draw(batch);
    }
    shipSprite.draw(batch);
    batch.end();
  }

  @Override
  public void resize(final Rect worldBounds) {
    shipSprite.resize(worldBounds);
    backgroundSprite.resize(worldBounds);
    for (AsteroidSprite asteroidSprite : asteroids) {
      asteroidSprite.resize(worldBounds);
    }
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
}
