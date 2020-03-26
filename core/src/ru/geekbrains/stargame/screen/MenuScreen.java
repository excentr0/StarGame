package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import ru.geekbrains.stargame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

  private Texture background;

  @Override
  public void show() {
    super.show();
    background = new Texture("space.png");
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    batch.draw(background, 0, 0);
    batch.end();
  }

  @Override
  public void dispose() {
    batch.dispose();
    background.dispose();
    super.dispose();
  }
}
