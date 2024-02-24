import java.util.Random;
import java.util.ArrayList;

public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        tileCount = 104;
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        this.shuffleTiles();
        final int totalNumberOfTilesDistributedForPlayer1 = 15;
        int indexOfLastTile = 0;
        
        for(int i = 0; i < players.length; i++)
        {
            if (i == 0)
            {
                for (int j = 0; j < totalNumberOfTilesDistributedForPlayer1; j++)
                {
                    indexOfLastTile++;
                    players[i].addTile(tiles[j]);
                }
            }

            else
            {
                for (int j = 0; j < totalNumberOfTilesDistributedForPlayer1 - 1; j++)
                {
                    indexOfLastTile++;
                    players[i].addTile(tiles[j + indexOfLastTile]);
                }
            }
        }

    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        Player currentPlayer = players[ this.getCurrentPlayerIndex()];
        currentPlayer.addTile( lastDiscardedTile);
        return  lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        Player currentPlayer = players[ this.getCurrentPlayerIndex()];
        tileCount--;
        Tile topTile = tiles[ tileCount];
        currentPlayer.addTile( topTile);
        Tile[] newTiles = new Tile[tileCount];

        for ( int i = 0; i < newTiles.length; i++)
        {
            newTiles[i] = tiles[i];
        }

        return topTile.toString();
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        Random rand = new Random();
        int randTiles;
        Tile temp = new Tile(14);
        for (int i = 0; i < 30; i++)
        {
            randTiles = rand.nextInt(tiles.length - 1);
            temp = tiles[i];
            tiles[i] = tiles[randTiles];
            tiles[randTiles] = temp;
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. use checkWinning method of the player class to determine
     */
    public boolean didGameFinish() {
        
        //A boolean which checks whether the game finishes or not
        boolean didGameFinish = false;
        int currPlayerIndex = this.getCurrentPlayerIndex();

        if (players[currPlayerIndex].checkWinning() == true)
        {
            didGameFinish = true;
        }
        
        return didGameFinish;
    }

    /* TODO: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    public Player[] getPlayerWithHighestLongestChain() {
        int maxLongestChain = 0;
        int sizeOfWinnersArray = 0;
        ArrayList<Player> winnersList = new ArrayList<>();
        
        for (int i = 0; i < players.length; i++)
        {
            if (players[i].findLongestChain() > maxLongestChain)
            {
                maxLongestChain = players[i].findLongestChain();
            }
        }

        for (int i = 0; i < players.length; i++)
        {
            if (players[i].findLongestChain() == maxLongestChain)
            {
                sizeOfWinnersArray++;
                winnersList.add(players[i]);
            }
        }

        Player[] winners = new Player[sizeOfWinnersArray];

        for (int i = 0; i < sizeOfWinnersArray; i++)
        {
            winners[i] = winnersList.get(i);
        }

        return winners;
    }
    
    /*
     * checks if there are more tiles on the stack to continue the game
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    /*
     * TODO: pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * you should check if getting the discarded tile is useful for the computer
     * by checking if it increases the longest chain length, if not get the top tile
     */
    public void pickTileForComputer() {
        Player currentPlayer = players[ this.getCurrentPlayerIndex()];
        int lastTileIndex = tileCount;
        if ( lastDiscardedTile.compareTo( tiles[ lastTileIndex]) > 0)
        {
            currentPlayer.addTile(lastDiscardedTile);
        }
        else
        {
            Tile topTile = tiles[ tileCount];
            currentPlayer.addTile( topTile);;
        }
    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     */
    public void discardTileForComputer() {
        Player currentPlayer = players[ this.getCurrentPlayerIndex()];
        Tile leastUsefulTile = currentPlayer.playerTiles[0];
        int leastUsefulTileIndex = 0;

        for ( int i = 1; i < currentPlayer.playerTiles.length; i++)
        {
            if ( leastUsefulTile.compareTo( currentPlayer.playerTiles[i]) <= 0)
            {
                leastUsefulTile = currentPlayer.playerTiles[i];
                leastUsefulTileIndex = i;
            }
        }
        
        currentPlayer.getAndRemoveTile( leastUsefulTileIndex);
        lastDiscardedTile = leastUsefulTile;
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        Player currentPlayer = players[ this.getCurrentPlayerIndex()];
        ArrayList <Tile> newPlayerTiles = new ArrayList <Tile> ();

        for ( int i = 0; i < currentPlayer.playerTiles.length; i++)
        {
            newPlayerTiles.add( currentPlayer.playerTiles[i]);
        }

        newPlayerTiles.remove( tileIndex);
        lastDiscardedTile = currentPlayer.playerTiles[ tileIndex];

        for ( int i = 0; i < newPlayerTiles.size(); i++)
        {
            currentPlayer.playerTiles[i] = newPlayerTiles.get( i);
        }

        currentPlayer.playerTiles[ currentPlayer.playerTiles.length - 1] = null;
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
