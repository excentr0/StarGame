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
  private Vector2 newPosition;
  private Vector2 dir;

  private final float speed = 20f;

  @Override
  public void show() {
    super.show();
    background = new Texture("space.png");
    smallShip = new Texture("small_ship.png");
    shipPosition = new Vector2();
    newPosition = new Vector2();
    dir = new Vector2();
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
    smallShip.dispose();
    super.dispose();
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    newPosition.set(screenX, Gdx.graphics.getHeight() - (float) screenY);
    dir = newPosition.cpy().sub(shipPosition).nor();
    System.out.println("dir=>" + dir);
    return true;
  }

  private void update(float delta) {
    if ((int) shipPosition.x != (int) newPosition.x) {
      shipPosition.x += dir.x * speed * delta;
    }
    if ((int) shipPosition.y != (int) newPosition.y) {
      shipPosition.y += dir.y * speed * delta;
    }
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
