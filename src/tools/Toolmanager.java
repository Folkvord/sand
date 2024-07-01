package tools;

import window.Board;

import java.awt.Graphics;

import entities.World;
import tools.brush.*;
import window.Mousehandler;

/*
 * Toolmanager skapes før board og etter world
 * I board skal createTools(board); kjøres for å skape verktøyene
 * og for å importere board. Board trenger toolmanager for å tegne verktøyene 
 */

public class Toolmanager {
    
    public Board board;
    public World world;
    public Mousehandler mousehandler;

    // Tools
    public Brush brush;
    public Rectangletool rectangletool;

    private String activeTool = "brush";


    public Toolmanager(World world){

        this.world = world;

    }


    public void createTools(Board board){

        this.board = board;
        this.mousehandler = board.mousehandler;

        // Børste
        brush = new Squarebrush(board, world);
        
        // rect. tool
        rectangletool = new Rectangletool(board, world);
        
    }

    public void changeParticleID(int particleID){

        brush.changeParticleID(particleID);

        rectangletool.changeParticleID(particleID);

    }

    
    // -----------------------------------<| Hent verktøy (for info) |>---------------------------------------- //

    public Brush getBrush(){
        return brush;
    }

    public Rectangletool getRectangletool(){
        return rectangletool;
    }

    // -----------------------------------<| Endre aktive verktøy |>---------------------------------------- //

    public void changeActiveTool(String tool){
        activeTool = tool;
    }

    public String activeTool(){
        return activeTool;
    }


    // -----------------------------------<| Børsten |>---------------------------------------- //
    
    public void changeBrush(int brushID){
        int radius = brush.getRadius();
        int particleID = brush.getParticleID();

        switch(brushID){

            case 1:
                brush = new Squarebrush(board, world, radius, particleID);
                break;
        
            case 2:
                brush = new Flatbrush(board, world, radius, particleID);
                break;    

            case 3:
                brush = new Circlebrush(board, world, radius, particleID);
                break;

        }

    }


    // -----------------------------------<| Velgere |>---------------------------------------- //

    public void useCorrectTool(){

        switch(activeTool){

            case "brush":
                useBrush();
                break;

            case "rectangle":
                useRectangletool();
                break;

        }
    
    }

    public void drawCorrectTool(Graphics g){

        switch(activeTool){

            case "brush":
                brush.drawMarker(g, board.getCurrentMouseX(), board.getCurrentMouseY());
                break;

            case "rectangle":
                rectangletool.drawRectangletool(g, board.getCurrentMouseX(), board.getCurrentMouseY());
                break;

            default:
                break;

        }

    }


    // -----------------------------------<| Bruksmetoder |>---------------------------------------- //

    private void useBrush(){

        if(mousehandler.leftClick){
                
            brush.paintVector(board.getLastMouseX(), board.getLastMouseY(), board.getCurrentMouseX(), board.getCurrentMouseY());
            
        }

    }

    private void useRectangletool(){

        // Start å lag et rektangel
        if(mousehandler.leftClick && !rectangletool.settingRectangle){
            rectangletool.startArea(board.getCurrentMouseX(), board.getCurrentMouseY());
        }
        // Lag rektangelet
        else if(!mousehandler.leftClick && rectangletool.settingRectangle){
            rectangletool.setArea(board.getCurrentMouseX(), board.getCurrentMouseY());
        }
        // Kanseller
        else if(mousehandler.rightClick && rectangletool.settingRectangle){
            rectangletool.cancel();
        }

    }


    

}