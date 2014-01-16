import storm

class SplitSentenceBolt(storm.BasicBolt):
    def process(self, tup):
        sentence = tup.values[0]
        storm.log("SplitSentenceBolt: "+sentence)
        words = sentence.split(" ")
        for word in words:
          storm.emit([word])

SplitSentenceBolt().run()