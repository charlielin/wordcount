import storm

class WordCountBolt(stomr.BasicBolt):
    def initialize(self, stormconf, context):
        counts = {}
    def process(self, tup):
        word = tup.values[0]
        if counts.has_key(word):
            count = counts[word]
        else:
            count = 0
        count += 1
        counts[word] = count
        storm.emit([word, count])

WordCountBolt().run()

