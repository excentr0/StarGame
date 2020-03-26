package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

  private Texture background;
  private Texture smallShip;
  private Vector2 shipPosition;
  private Vector2 v;

  @Override
  public void show() {
    super.show();
    background = new Texture("space.png");
    smallShip = new Texture("small_ship.png");
    shipPosition = new Vector2();
    v = new Vector2(3, 3);
  }

  @Override
  public void render(float delta) {
    update(delta);
    draw();
  }

  @Override
  public void dispose() {
    batch.dispose();
    background.dispose();
    super.dispose();
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    shipPosition.set(screenX, Gdx.graphics.getHeight() - (float) screenY);
    return false;
  }

  private void update(float delta) {
    shipPosition.add(v);
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    batch.draw(background, 0, 0);
    batch.draw(smallShip, shipPosition.x, shipPosition.y);
    batch.end();
  }
}
