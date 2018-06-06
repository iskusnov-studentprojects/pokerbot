import numpy as np

class Card:
    def __init__(self, value, suit):
        self.value = value
        self.suit = suit

class Game:
    def checkCombination(self, hand, table):
        cards = np.array(hand, table)
