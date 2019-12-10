package org.polytechtours.javaperformance.tp.paintingants;
// package PaintingAnts_v2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

// version : 2.0

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * <p>
 * Titre : Painting Ants
 * </p>
 * <p>
 * Description :
 * </p>
 * <p>
 * Copyright : Copyright (c) 2003
 * </p>
 * <p>
 * Société : Equipe Réseaux/TIC - Laboratoire d'Informatique de l'Université de
 * Tours
 * </p>
 *
 * @author Nicolas Monmarché
 * @version 1.0
 */

public class CPainting extends Canvas implements MouseListener {
  private static final long serialVersionUID = 1L;
  // matrice servant pour le produit de convolution
  private float[][] mMatriceConv;
  // Objet de type Graphics permettant de manipuler l'affichage du Canvas
  private Graphics mGraphics;
  // Objet ne servant que pour les bloc synchronized pour la manipulation du
  // tableau des couleurs
  private Object mMutexCouleurs = new Object();
  // tableau des couleurs, il permert de conserver en memoire l'état de chaque
  // pixel du canvas, ce qui est necessaire au deplacemet des fourmi
  // il sert aussi pour la fonction paint du Canvas
  private Color[][] mCouleurs;
  // couleur du fond
  private Color mCouleurFond = new Color(255, 255, 255);
  // dimensions
  private Dimension mDimension = new Dimension();

    private PaintingAnts mApplis;

    private boolean mSuspendu = false;

    /******************************************************************************
     * Titre : public CPainting() Description : Constructeur de la classe
     ******************************************************************************/
    public CPainting(Dimension pDimension, PaintingAnts pApplis) {
        int i, j;
        addMouseListener(this);

        mApplis = pApplis;

        mDimension = pDimension;
        setBounds(new Rectangle(0, 0, mDimension.width, mDimension.height));

        this.setBackground(mCouleurFond);

        // initialisation de la matrice des couleurs
        mCouleurs = new Color[mDimension.width][mDimension.height];
        synchronized (mMutexCouleurs) {
            for (i = 0; i != mDimension.width; i++) {
                for (j = 0; j != mDimension.height; j++) {
                    mCouleurs[i][j] = new Color(mCouleurFond.getRed(), mCouleurFond.getGreen(), mCouleurFond.getBlue());
                }
            }
        }

    }

    /******************************************************************************
     * Titre : Color getCouleur Description : Cette fonction renvoie la couleur
     * d'une case
     ******************************************************************************/
    public Color getCouleur(int x, int y) {
        synchronized (mMutexCouleurs) {
            return mCouleurs[x][y];
        }
    }

    /******************************************************************************
     * Titre : Color getDimension Description : Cette fonction renvoie la
     * dimension de la peinture
     ******************************************************************************/
    public Dimension getDimension() {
        return mDimension;
    }

    /******************************************************************************
     * Titre : Color getHauteur Description : Cette fonction renvoie la hauteur de
     * la peinture
     ******************************************************************************/
    public int getHauteur() {
        return mDimension.height;
    }

    /******************************************************************************
     * Titre : Color getLargeur Description : Cette fonction renvoie la hauteur de
     * la peinture
     ******************************************************************************/
    public int getLargeur() {
        return mDimension.width;
    }

    /******************************************************************************
     * Titre : void init() Description : Initialise le fond a la couleur blanche
     * et initialise le tableau des couleurs avec la couleur blanche
     ******************************************************************************/


  public void initMatriceConv9() {
    // initialisation de la matrice de convolution : lissage moyen sur 9
    // cases
    /*
     * 1 2 1 2 4 2 1 2 1
     */
    mMatriceConv = new float[3][3];

    mMatriceConv[0][0] = 1 / 16f;
    mMatriceConv[0][1] = 2 / 16f;
    mMatriceConv[0][2] = 1 / 16f;
    mMatriceConv[1][0] = 2 / 16f;
    mMatriceConv[1][1] = 4 / 16f;
    mMatriceConv[1][2] = 2 / 16f;
    mMatriceConv[2][0] = 1 / 16f;
    mMatriceConv[2][1] = 2 / 16f;
    mMatriceConv[2][2] = 1 / 16f;

  }

  public void initMatriceConv25() {
    // initialisation de la matrice de convolution : lissage moyen sur 25
    // cases
    /*
     * 1 1 2 1 1 1 2 3 2 1 2 3 4 3 2 1 2 3 2 1 1 1 2 1 1
     */
    mMatriceConv = new float[5][5];

    mMatriceConv[0][0] = 1 / 44f;
    mMatriceConv[0][1] = 1 / 44f;
    mMatriceConv[0][2] = 2 / 44f;
    mMatriceConv[0][3] = 1 / 44f;
    mMatriceConv[0][4] = 1 / 44f;
    mMatriceConv[1][0] = 1 / 44f;
    mMatriceConv[1][1] = 2 / 44f;
    mMatriceConv[1][2] = 3 / 44f;
    mMatriceConv[1][3] = 2 / 44f;
    mMatriceConv[1][4] = 1 / 44f;
    mMatriceConv[2][0] = 2 / 44f;
    mMatriceConv[2][1] = 3 / 44f;
    mMatriceConv[2][2] = 4 / 44f;
    mMatriceConv[2][3] = 3 / 44f;
    mMatriceConv[2][4] = 2 / 44f;
    mMatriceConv[3][0] = 1 / 44f;
    mMatriceConv[3][1] = 2 / 44f;
    mMatriceConv[3][2] = 3 / 44f;
    mMatriceConv[3][3] = 2 / 44f;
    mMatriceConv[3][4] = 1 / 44f;
    mMatriceConv[4][0] = 1 / 44f;
    mMatriceConv[4][1] = 1 / 44f;
    mMatriceConv[4][2] = 2 / 44f;
    mMatriceConv[4][3] = 1 / 44f;
    mMatriceConv[4][4] = 1 / 44f;
  }

  public void initMatriceConv49() {
    // initialisation de la matrice de convolution : lissage moyen sur 49
    // cases
    /*
     * 1 1 2 2 2 1 1 1 2 3 4 3 2 1 2 3 4 5 4 3 2 2 4 5 8 5 4 2 2 3 4 5 4 3 2 1 2
     * 3 4 3 2 1 1 1 2 2 2 1 1
     */
    mMatriceConv = new float[7][7];

    mMatriceConv[0][0] = 1 / 128f;
    mMatriceConv[0][1] = 1 / 128f;
    mMatriceConv[0][2] = 2 / 128f;
    mMatriceConv[0][3] = 2 / 128f;
    mMatriceConv[0][4] = 2 / 128f;
    mMatriceConv[0][5] = 1 / 128f;
    mMatriceConv[0][6] = 1 / 128f;

    mMatriceConv[1][0] = 1 / 128f;
    mMatriceConv[1][1] = 2 / 128f;
    mMatriceConv[1][2] = 3 / 128f;
    mMatriceConv[1][3] = 4 / 128f;
    mMatriceConv[1][4] = 3 / 128f;
    mMatriceConv[1][5] = 2 / 128f;
    mMatriceConv[1][6] = 1 / 128f;

    mMatriceConv[2][0] = 2 / 128f;
    mMatriceConv[2][1] = 3 / 128f;
    mMatriceConv[2][2] = 4 / 128f;
    mMatriceConv[2][3] = 5 / 128f;
    mMatriceConv[2][4] = 4 / 128f;
    mMatriceConv[2][5] = 3 / 128f;
    mMatriceConv[2][6] = 2 / 128f;

    mMatriceConv[3][0] = 2 / 128f;
    mMatriceConv[3][1] = 4 / 128f;
    mMatriceConv[3][2] = 5 / 128f;
    mMatriceConv[3][3] = 8 / 128f;
    mMatriceConv[3][4] = 5 / 128f;
    mMatriceConv[3][5] = 4 / 128f;
    mMatriceConv[3][6] = 2 / 128f;

    mMatriceConv[4][0] = 2 / 128f;
    mMatriceConv[4][1] = 3 / 128f;
    mMatriceConv[4][2] = 4 / 128f;
    mMatriceConv[4][3] = 5 / 128f;
    mMatriceConv[4][4] = 4 / 128f;
    mMatriceConv[4][5] = 3 / 128f;
    mMatriceConv[4][6] = 2 / 128f;

    mMatriceConv[5][0] = 1 / 128f;
    mMatriceConv[5][1] = 2 / 128f;
    mMatriceConv[5][2] = 3 / 128f;
    mMatriceConv[5][3] = 4 / 128f;
    mMatriceConv[5][4] = 3 / 128f;
    mMatriceConv[5][5] = 2 / 128f;
    mMatriceConv[5][6] = 1 / 128f;

    mMatriceConv[6][0] = 1 / 128f;
    mMatriceConv[6][1] = 1 / 128f;
    mMatriceConv[6][2] = 2 / 128f;
    mMatriceConv[6][3] = 2 / 128f;
    mMatriceConv[6][4] = 2 / 128f;
    mMatriceConv[6][5] = 1 / 128f;
    mMatriceConv[6][6] = 1 / 128f;
  }
  /******************************************************************************
   * Titre : void init() Description : Initialise le fond a la couleur blanche
   * et initialise le tableau des couleurs avec la couleur blanche
   ******************************************************************************/
  public void init() {
    int i, j;
    mGraphics = getGraphics();
    synchronized (mMutexCouleurs) {
      mGraphics.clearRect(0, 0, mDimension.width, mDimension.height);

      // initialisation de la matrice des couleurs

      for (i = 0; i != mDimension.width; i++) {
        for (j = 0; j != mDimension.height; j++) {
          mCouleurs[i][j] = new Color(mCouleurFond.getRed(), mCouleurFond.getGreen(), mCouleurFond.getBlue());
        }
      }
    }



    mSuspendu = false;
  }

    /****************************************************************************/
    public void mouseClicked(MouseEvent pMouseEvent) {
        pMouseEvent.consume();
        if (pMouseEvent.getButton() == MouseEvent.BUTTON1) {
            // double clic sur le bouton gauche = effacer et recommencer
            if (pMouseEvent.getClickCount() == 2) {
                init();
            }
            // simple clic = suspendre les calculs et l'affichage
            mApplis.pause();
        } else {
            // bouton du milieu (roulette) = suspendre l'affichage mais
            // continuer les calculs
            if (pMouseEvent.getButton() == MouseEvent.BUTTON2) {
                suspendre();
            } else {
                // clic bouton droit = effacer et recommencer
                // case pMouseEvent.BUTTON3:
                init();
            }
        }
    }

    /****************************************************************************/
    public void mouseEntered(MouseEvent pMouseEvent) {
    }

    /****************************************************************************/
    public void mouseExited(MouseEvent pMouseEvent) {
    }

    /****************************************************************************/
    public void mousePressed(MouseEvent pMouseEvent) {

    }

    /****************************************************************************/
    public void mouseReleased(MouseEvent pMouseEvent) {
    }

    /******************************************************************************
     * Titre : void paint(Graphics g) Description : Surcharge de la fonction qui
     * est appelé lorsque le composant doit être redessiné
     ******************************************************************************/
    @Override
    public void paint(Graphics pGraphics) {
        int i, j;

        synchronized (mMutexCouleurs) {
            for (i = 0; i < mDimension.width; i++) {
                for (j = 0; j < mDimension.height; j++) {
                    pGraphics.setColor(mCouleurs[i][j]);
                    pGraphics.fillRect(i, j, 1, 1);
                }
            }
        }
    }


    public void convolution(int x, int y, Color c, int pTaille) {
        int i, j, k, l, m, n;
        float R, G, B;
        Color lColor;

        switch (pTaille) {
            case 1:
                initMatriceConv9();
                break;
            case 2:
                initMatriceConv25();
                break;
            case 3:
                initMatriceConv49();
                break;
        }

        // produit de convolution discrete sur 9 cases
        for (i = 0; i < 2 * pTaille + 1; i++) {

            for (j = 0; j < 2 * pTaille + 1; j++) {

                R = G = B = 0f;

                for (k = 0; k < 2 * pTaille; k++) {

                    for (l = 0; l < 2 * pTaille; l++) {

                        m = (x + i + k - 2 * pTaille + mDimension.width) % mDimension.width;
                        n = (y + j + l - 2 * pTaille + mDimension.height) % mDimension.height;

                        R += mMatriceConv[k][l] * mCouleurs[m][n].getRed();
                        G += mMatriceConv[k][l] * mCouleurs[m][n].getGreen();
                        B += mMatriceConv[k][l] * mCouleurs[m][n].getBlue();

                    }

                }

                lColor = new Color((int) R, (int) G, (int) B);

                mGraphics.setColor(lColor);

                m = (x + i - pTaille + mDimension.width) % mDimension.width;
                n = (y + j - pTaille + mDimension.height) % mDimension.height;

                mCouleurs[m][n] = lColor;

                if (!mSuspendu) {
                    mGraphics.fillRect(m, n, 1, 1);
                }

            }
        }

        synchronized (mMutexCouleurs) {
            if (!mSuspendu) {
                // on colorie la case sur laquelle se trouve la fourmi
                mGraphics.setColor(c);
                mGraphics.fillRect(x, y, 1, 1);
            }

            mCouleurs[x][y] = c;

            // on fait diffuser la couleur avec une taille de diffusion pTaille:
            if (pTaille != 0) {
                convolution(x, y, c, pTaille);
            }
        }
    }

    /******************************************************************************
     * Titre : setSupendu Description : Cette fonction change l'état de suspension
     ******************************************************************************/

    public void suspendre() {
        mSuspendu = !mSuspendu;
        if (!mSuspendu) {
            repaint();
        }
    }
}
