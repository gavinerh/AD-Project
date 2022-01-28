class NewsObject:
    def __init__(self, status, totalResults, articles):
        self.status, self.totalResults, self.articles = status, totalResults, articles

class Details:
    def __init__(self, source, author, title, description, url, urlToImage, publishedAt, content):
        self.source, self.author, self.title, self.description, self.url, self.urlToImage, self.publishedAt, self.content = source, author, title, description, url, urlToImage, publishedAt, content

class Source:
    def __init__(self, id, name):
        