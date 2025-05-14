```mermaid
flowchart LR
    subgraph Docker
        subgraph " "
            CURL([Curl])
            subgraph SpringBoot Applications
                COM[Company]:::cyanBox
                RSA[Resource Server A]:::greenBox
                RSB[Resource Server B]:::yellowBox
            end
        end
    end
%% Flows
    CURL <== plain<br> text ==> COM
    CURL <== plain<br> text ==> RSA
    CURL <== plain<br> text ==> RSB
%% Style Definitions
    style Docker fill: lightblue
    classDef greenBox fill: #00ff00, stroke: #000, stroke-width: 3px
    classDef cyanBox fill: #00ffff, stroke: #000, stroke-width: 3px
    classDef yellowBox fill: #ffff00, stroke: #000, stroke-width: 3px
```
```mermaid
flowchart LR
    subgraph SpringBoot Applications
        COM[Company]:::cyanBox
        RSA[Resource Server A]:::greenBox
        RSB[Resource Server B]:::yellowBox
    end
    CURL([Curl])
%% Flows
    CURL <== plain<br> text ==> COM
    CURL <== plain<br> text ==> RSA
    CURL <== plain<br> text ==> RSB
%% Style Definitions
    classDef greenBox fill: #00ff00, stroke: #000, stroke-width: 3px
    classDef cyanBox fill: #00ffff, stroke: #000, stroke-width: 3px
    classDef yellowBox fill: #ffff00, stroke: #000, stroke-width: 3px
```