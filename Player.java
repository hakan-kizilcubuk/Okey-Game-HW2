import java.util.ArrayList;

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    public boolean checkWinning() 
    {
        
        int first_tile=this.playerTiles[0].getValue();
        int last_tile=this.playerTiles[14].getValue();

        int difference= last_tile-first_tile;
        
        for(int i=0;i<14;i++)
        {
            for(int a=i+1;a<15;a++)
            {
                if(this.playerTiles[i]==this.playerTiles[a])
                {
                    return false;
                }
            }
        }
        
        if(difference!=13)
        {
            return false;
        }
        
        return true;
    }

    /*
     * TODO: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */
    public int findLongestChain() 
    {
        int longestChain = 0;

        int current_chain= 1;

        ArrayList<Integer> values_title= new ArrayList<Integer>();

        for(int i=0;i<15;i++)
        {
            values_title.add(this.playerTiles[i].getValue());
        }
        
        for(int a=0;a<values_title.size()-1;a++)
        {
            for(int b=a+1;b<values_title.size();b++)
            {
                if(values_title.get(a)==values_title.get(b))
                {
                    values_title.remove(b);
                }
            }
        }
        
        int[] values_to_sort=new int[values_title.size()+1];
        
        for(int i=0;i<values_to_sort.length;i++)
        {
            values_to_sort[i]=values_title.get(i);
        }
        
       
        int temp;

        for(int n=0;n<values_to_sort.length-1;n++)
        {
            for(int m=0;m<values_to_sort.length;m++)
            {
                if(values_to_sort[m]<values_to_sort[n])
                {
                    temp=values_to_sort[n];
                    values_to_sort[n]=values_to_sort[m];
                    values_to_sort[m]=temp;
                }
            }
        }
        
        int x=0;

        while(x<values_to_sort.length-1)
        {
            if(values_to_sort[x+1]==values_to_sort[x]+1)
            {
                current_chain++;
            }
            else
            {
                if(current_chain>longestChain)
                {
                    longestChain=current_chain;
                    current_chain=0;
                }
            }
            
            x++;
        }
        
        return longestChain;
    }

    /*
     * TODO: removes and returns the tile in given index position
     */
    public Tile getAndRemoveTile(int index) 
    {
        Tile tileToGet = this.playerTiles[index];

        ArrayList<Tile> tempList = new ArrayList<Tile>();
        
        for(int i=0;i<this.playerTiles.length;i++)
        {
            tempList.add(this.playerTiles[i]);
        }
        
        tempList.remove(index);

        for(int m=0;m<this.playerTiles.length-1;m++)
        {
            this.playerTiles[m]=tempList.get(m);
        }
        
        return tileToGet;
    }

    /*
     * TODO: adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */
    public void addTile(Tile t) {

    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
