import time
import random
import storm

class RandomSentenceSpout(storm.Spout):
    sentences = [
        "the cow jumped over the moon",
        "an apple a day keeps the doctor away",
        "four score and seven years ago",
        "snow white and the seven dwarfs",
        "i am at two with nature"
    ]
    def nextTuple(self):
        time.sleep(1.00)
        sentence = self.sentences[random.randint(0, len(self.sentences) - 1)]
        storm.log('RandomSentenceSpout, storm.emit: ' + sentence)
        storm.emit([sentence])

RandomSentenceSpout().run()
