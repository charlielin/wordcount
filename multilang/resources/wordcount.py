import storm

class WordCountBolt(storm.BasicBolt):
    counts = {}
    def process(self, tup):
        word = tup.values[0]
        if self.counts.has_key(word):
            count = self.counts[word]
        else:
            count = 0
        count += 1
        self.counts[word] = count
        storm.log(str(word)+" "+str(count))
        storm.emit([word, count])

WordCountBolt().run()

