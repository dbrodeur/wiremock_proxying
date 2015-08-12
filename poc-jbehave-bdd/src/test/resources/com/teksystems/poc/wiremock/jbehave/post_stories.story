Given a post
When id is 1
Then the title must be sunt aut facere repellat provident occaecati excepturi optio reprehenderit

Given a post
When id is 1000
Then the title must be post 1000's title

Given a post
When id is 2000
And we have no post data available
Then the title must be post 2000's title