import sys

files = {}
error_files = []
alphabe = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]
ships = {"C": "Carrier", "B": "Battleship", "D": "Destroyer", "S": "Submarine", "P": "Patrol Boat"}
emptyboard1, emptyboard2, playersboard = {}, {}, {}
Carrier1, Battleship1, Destroyer1, Submarine1, PatrolBoat1 = ["-"], ["-", "-"], ["-"], ["-"], ["-", "-", "-", "-"]
Carrier2, Battleship2, Destroyer2, Submarine2, PatrolBoat2 = ["-"], ["-", "-"], ["-"], ["-"], ["-", "-", "-", "-"]
player1 = []
player2 = []
shipsshortcut1 = {"C": Carrier1, "B": Battleship1, "D": Destroyer1, "S": Submarine1, "P": PatrolBoat1}
shipsshortcut2 = {"C": Carrier2, "B": Battleship2, "D": Destroyer2, "S": Submarine2, "P": PatrolBoat2}
 #  This part is my data structures
f = open("OptionalPlayer1.txt", "r")
k = open("OptionalPlayer2.txt", "r")
out = open("Battleship.out","w")
optionalplayer1 = f.read().splitlines()
optionalplayer2 = k.read().splitlines()
OptionalPlayer1, OptionalPlayer2 = [], []
 # I opeed here optional player txt files
def outputFunction(output): #  This is my output function, I use end function in print
    print(output,end="")
    out.writelines(output)

 #  This part also split optional player txt files
for a in optionalplayer1:
    b = []
    b.extend(a.split(";")[0].split(":"))
    b.append(a.split(";")[1].rstrip(";"))
    OptionalPlayer1.append(b)
for a in optionalplayer2:
    b = []
    b.extend(a.split(";")[0].split(":"))
    b.append(a.split(";")[1].rstrip(";"))
    OptionalPlayer2.append(b)

 #  Here, If you write missing argument, except catch this and give index error
try:
    gameturn, gameboardname = [sys.argv[3], sys.argv[4]], [sys.argv[1], sys.argv[2]]
except IndexError:
    outputFunction("IndexError: Missing Argument")
    exit()

 #  If you write wrong name in command line, this part catch them and give error
for i in sys.argv[1:5]:
    try:
        datas = open(i, "r")
        files.update({i: datas.read().splitlines()})
    except IOError:
        error_files.append(i)
errors = ",".join(error_files)
if errors:
    outputFunction(f"IOError: input files {errors}  is/are not reachable.")
    exit()
else: #  If there isn't error in command line, my code starts here
    for j in gameturn:
        files[j] = ''.join(files[j]).rstrip(";")
        files[j] = ''.join(files[j]).split(";")
for x in range(1, 11): #  I created players empty boards here
    for j in alphabe:
        emptyboard1[(int(x), j)] = "-"
        emptyboard2[(int(x), j)] = "-"

for x in gameboardname: #  I created players game boards here.
    playersboard[x] = {}
    for j in range(len(files[x])):
        numbers = files[x][j].split(";")
        for a in alphabe:
            playersboard[x][(int(j + 1), a)] = numbers[alphabe.index(a)]
            if playersboard[x][(int(j + 1), a)] == "":
                playersboard[x][(int(j + 1), a)] = "-"
 #  These are ships lists for each player
carrier1, battleship1, destroyer1, submarine1, patrolboat1 = [], [], [], [], []
carrier2, battleship2, destroyer2, submarine2, patrolboat2 = [], [], [], [], []
for key, value in playersboard["Player1.txt"].items(): #  Here I find the three type of ship and append to my ship lists for each player
    if value == "C":
        carrier1.append(key)
        player1.append(key)
    elif value == "D":
        destroyer1.append(key)
        player1.append(key)
    elif value == "S":
        submarine1.append(key)
        player1.append(key)
for key, value in playersboard["Player2.txt"].items():
    if value == "C":
        carrier2.append(key)
        player2.append(key)
    elif value == "D":
        destroyer2.append(key)
        player2.append(key)
    elif value == "S":
        submarine2.append(key)
        player2.append(key)


def battleship(optional, battleship, player): #  This function uses optional player txt files and finds the players battleships locations
    for m in optional:
        ship = []
        if "B" in m[0]:
            n, l = m[1].split(",")
            if m[2] == "right":
                a = alphabe.index(l)
                ship = [(int(n), alphabe[a]), (int(n), alphabe[a + 1]), (int(n), alphabe[a + 2]),
                        (int(n), alphabe[a + 3])]
                battleship.append(ship)
                player.extend(ship)
            else:
                ship = [(int(n), l), (int(n) + 1, l), (int(n) + 2, l), (int(n) + 3, l)]
                battleship.append(ship)
                player.extend(ship)


def patrolboat(optional, battleship, player): #  This function uses optional player txt files and finds the players patrol boats locations
    for m in optional:
        ship = []
        if "P" in m[0]:
            n, l = m[1].split(",")
            if m[2] == "right":
                a = alphabe.index(l)
                ship = [(int(n), alphabe[a]), (int(n), alphabe[a + 1])]
                battleship.append(ship)
                player.extend(ship)
            else:
                ship = [(int(n), l), (int(n) + 1, l)]
                battleship.append(ship)
                player.extend(ship)

 #  I ran the functions here
battleship(OptionalPlayer1, battleship1, player1)
battleship(OptionalPlayer2, battleship2, player2)
patrolboat(OptionalPlayer1, patrolboat1, player1)
patrolboat(OptionalPlayer2, patrolboat2, player2)

 #  I created dictionarys,this dictionaries include each players ships
lists2 = {"Carrier": carrier2, "Battleship": battleship2, "Destroyer": destroyer2, "Submarine": submarine2,
          "Patrol Boat": patrolboat2}
lists1 = {"Carrier": carrier1, "Battleship": battleship1, "Destroyer": destroyer1, "Submarine": submarine1,
          "Patrol Boat": patrolboat1}


def shipsdelete(listname, ship, shortcut, player, l): #  This function deletes if the ship part in location that players hit and turn there "X"
    if l == "C" or l == "S" or l == "D":
        listname[ship].remove((int(number), letter))
        player.remove((int(number), letter))
        if listname[ship]:
            pass
        else:
            shortcut[-1] = "X"
            shortcut.sort()
            shortcut.reverse()
    else:
        for a in range(len(listname[ship])):
            try:
                listname[ship][a].remove((int(number), letter))
                player.remove((int(number), letter))
                if listname[ship][a]:
                    pass
                else:
                    shortcut[-1] = "X"
                    shortcut.sort()
                    shortcut.reverse()
            except:
                pass

 #  These two functions plays the game, they just need where the player wants to hit
def player1move(number, letter):
    if playersboard["Player2.txt"][(int(number), letter)] == "-":
        emptyboard2[(int(number), letter)] = "O"
        playersboard["Player2.txt"][(int(number), letter)] = "O"
    else:
        shipsdelete(lists2, ships.get(playersboard["Player2.txt"][(int(number), letter)]),
                    shipsshortcut2.get(playersboard["Player2.txt"][(int(number), letter)]), player2,
                    playersboard["Player2.txt"][(int(number), letter)])
        emptyboard2[(int(number), letter)] = "X"
        playersboard["Player2.txt"][(int(number), letter)] = "X"


def player2move(number, letter):
    if playersboard["Player1.txt"][(int(number), letter)] == "-":
        emptyboard1[(int(number), letter)] = "O"
        playersboard["Player1.txt"][(int(number), letter)] = "O"
    else:
        shipsdelete(lists1, ships.get(playersboard["Player1.txt"][(int(number), letter)]),
                    shipsshortcut1.get(playersboard["Player1.txt"][(int(number), letter)]), player1,
                    playersboard["Player1.txt"][(int(number), letter)])
        emptyboard1[(int(number), letter)] = "X"
        playersboard["Player1.txt"][(int(number), letter)] = "X"


def boards(board1, board2): #  This function write game borads to out txt and terminal
    outputFunction("  " + ' '.join(alphabe) + "\t\t" + "  " + ' '.join(alphabe)+"\n")
    for x in range(1, 11):
        outputFunction("%-2s" % f"{x}")
        for a in alphabe:
            if a == "J":
                outputFunction(board1[(x, a)])
            else:
                outputFunction(board1[(x, a)]+" ")
        outputFunction("\t\t%-2s" % f"{x}")
        for a in alphabe:
            outputFunction(board2[(x, a)]+" ")
        outputFunction("\n")
    outputFunction("\n")
    outputFunction(f"Carrier\t\t{''.join(Carrier1)}\t\t\t\tCarrier\t\t{''.join(Carrier2)}\n")
    outputFunction(f"Battleship\t{' '.join(Battleship1)}\t\t\t\tBattleship\t{' '.join(Battleship2)}\n")
    outputFunction(f"Destroyer\t{''.join(Destroyer1)}\t\t\t\tDestroyer\t{''.join(Destroyer2)}\n")
    outputFunction(f"Submarine\t{''.join(Submarine1)}\t\t\t\tSubmarine\t{''.join(Submarine2)}\n")
    outputFunction(f"Patrol Boat\t{' '.join(PatrolBoat1)}\t\t\tPatrol Boat\t{' '.join(PatrolBoat2)}\n")

def indexError(player): #  This function raises a special IndexError for our game
    if not any(files[player][i // 2]) or "," not in files[player][i // 2]:
        raise IndexError(f"IndexError: {turn}\n")
    elif "," in files[player][i // 2]:
        n, l = files[player][i // 2].split(",")
        if not any(n) or not any(l):
            raise IndexError(f"IndexError: {turn}\n")

def errormessages(player,gameTurn,i,e): #  I just want to use lots of function and I did something like that, this write a error messages for every given error
    global situation
    if situation >= 2:
        i += 1
        outputFunction(f"{e}")
        if playername == "Player1":
            files[playername + ".in"].remove(gameTurn)
            situation = 2
            i -= 1
        elif playername == "Player2":
            files[playername + ".in"].remove(gameTurn)
            situation = 3
            i -= 1
    else:
        i += 1
        outputFunction(f"{player}'s Move\n\nRound : {i // 2 + 1}\t\t\t\t\tGrid Size 10x10\n")
        outputFunction("Player1's Hidden Board\t\tPlayer2's Hidden Board\n")
        boards(newboard1, newboard2)
        outputFunction(f"\nEnter your move : {gameTurn}\n")
        outputFunction(f"{e}")
        if playername == "Player1":
            files[playername + ".in"].remove(gameTurn)
            situation = 2
            i -= 1
        elif playername == "Player2":
            files[playername + ".in"].remove(gameTurn)
            situation = 3
            i -= 1

outputFunction("Battle of Ships Game\n\n")
situation,i = 0,0 #  The situation means is which player plays the game
while i < min(len(files["Player1.in"]),len(files["Player2.in"]))*2: #  Here my all game plays
    try:
        newboard1 = emptyboard1
        newboard2 = emptyboard2
        if situation == 0:
            turn = files["Player1.in"][i // 2] #  I completely divide i with 2 because I need 0,0,1,1,2,2... this is works for my code
            playername = "Player1"
            indexError("Player1.in")
            number, letter = files["Player1.in"][i // 2].split(",")
            assert int(number) <= 10, "AssertionError: Invalid Operation\n"
            assert alphabe.index(letter) <= 9, "AssertionError: Invalid Operation\n"
            outputFunction(f"Player1's Move\n\nRound : {i // 2 + 1}\t\t\t\t\tGrid Size 10x10\n\n")
            outputFunction("Player1's Hidden Board\t\tPlayer2's Hidden Board\n")
            boards(newboard1, newboard2)
            outputFunction(f"\nEnter your move: {number + ',' + letter}\n\n")
            newboard2 = player1move(number, letter)
            situation = 1
            i +=1
        elif situation == 2: #  If situation equals to 2 or 3,this mean player's move is incorrect
            playername = "Player1"
            turn = files["Player1.in"][i // 2]
            indexError("Player1.in")
            number, letter = files["Player1.in"][i // 2].split(",")
            outputFunction(f"Enter your move: {number + ',' + letter}\n")
            assert int(number) <= 10, "AssertionError: Invalid Operation\n"
            assert alphabe.index(letter) <= 9, "AssertionError: Invalid Operation\n"
            newboard2 = player1move(number, letter)
            outputFunction("\n")
            situation = 1
            i += 1
        elif situation == 1:
            turn = files["Player2.in"][i // 2]
            playername = "Player2"
            indexError("Player2.in")
            number, letter = files["Player2.in"][i // 2].split(",")
            assert int(number) <= 10, "AssertionError: Invalid Operation\n"
            assert alphabe.index(letter) <= 9, "AssertionError: Invalid Operation\n"
            outputFunction(f"Player2's Move\n\nRound : {i // 2 + 1}\t\t\t\t\tGrid Size 10x10\n\n")
            outputFunction("Player1's Hidden Board\t\tPlayer2's Hidden Board\n")
            boards(newboard1, newboard2)
            outputFunction(f"\nEnter your move: {number + ',' + letter}\n\n")
            newboard1 = player2move(number, letter)
            situation = 0
            i +=1
        elif situation == 3:
            playername = "Player2"
            turn = files["Player2.in"][i // 2]
            indexError("Player2.in")
            number, letter = files["Player2.in"][i // 2].split(",")
            outputFunction(f"Enter your move: {number + ',' + letter}\n")
            assert int(number) <= 10, "AssertionError: Invalid Operation\n"
            assert alphabe.index(letter) <= 9, "AssertionError: Invalid Operation\n"
            newboard1 = player2move(number, letter)
            outputFunction("\n")
            situation = 1
            i += 1
        if i %2 == 0: #  The game round end when i becoma 0,2,4,6,8 so I checked the winner when i turn them
            if not any(player1 + player2):
                outputFunction("It's a Draw!\n\n")
                outputFunction("Player1's Board\t\t\t\tPlayer2's Board\n")
                boards(playersboard["Player1.txt"], playersboard["Player2.txt"])
                exit()
            elif not any(player1):
                outputFunction("Player2 Wins!\n\nFinal Information\n\n")
                outputFunction("Player1's Board\t\t\t\tPlayer2's Board\n")
                boards(playersboard["Player1.txt"], playersboard["Player2.txt"])
                exit()
            elif not any(player2):
                outputFunction("Player1 Wins!\n\nFinal Information\n\n")
                outputFunction("Player1's Board\t\t\tPlayer2's Board\n")
                boards(playersboard["Player1.txt"], playersboard["Player2.txt"])
                exit()
    except ValueError: #  I didn't raise value error so I need to write this error different
        errormessages(playername,turn,i,f"ValueError:{turn}\n")
    except (IndexError,AssertionError) as e: #  I raised these function and called error messages function in this except, this function very usefull
        errormessages(playername,turn,i,e)
    except Exception:
        outputFunction(f"kaBoom: run for your life!\n")
        exit()
 # Mustafa Kaan Çevik
 # 2210356101