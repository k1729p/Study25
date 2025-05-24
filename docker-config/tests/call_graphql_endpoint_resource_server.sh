#!/usr/bin/env bash
SITE="http://$1:$2/graphql"
BRIGHT_RED='\033[1;31m'
BRIGHT_GREEN='\033[1;32m'
#BRIGHT_BLUE='\033[1;34m'
BRIGHT_CYAN='\033[1;36m'
#BRIGHT_MAGENTA='\033[1;35m'
BRIGHT_YELLOW='\033[1;33m'
#BRIGHT_WHITE='\033[1;37m'
NC='\033[0m' # No Color
PASSWORD="clandestine"
USERS=("alice" "bob" "charlie" "david")
LEVELS=("Secret" "Confidential" "Restricted" "Official")

for i in "${!USERS[@]}"; do
    USER="${USERS[$i]}"
    LEVEL="${LEVELS[$i]}"

    echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"
    echo -e "${BRIGHT_CYAN}MUTATE DOCUMENT WITH USER $USER AND LEVEL $LEVEL${NC}"
    curl -s -g -H "Accept: application/json" -H "Content-Type: application/json" \
        -X POST "$SITE" -u "$USER:$PASSWORD" -d "@queries/resource-server/mutateDocument_${LEVEL}.txt"
    echo
done

for i in "${!USERS[@]}"; do
    USER="${USERS[$i]}"
    LEVEL="${LEVELS[$i]}"

    echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"
    echo -e "${BRIGHT_CYAN}QUERY DOCUMENT WITH USER $USER AND LEVEL $LEVEL${NC}"
    curl -s -g -H "Accept: application/json" -H "Content-Type: application/json" \
        -X POST "$SITE" -u "$USER:$PASSWORD" -d "@queries/resource-server/queryDocument_${LEVEL}.txt" | jq .
    echo
done

USERS=("bob" "charlie" "david")
for i in "${!USERS[@]}"; do
    USER="${USERS[$i]}"
    echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"
    echo -e "${BRIGHT_RED}QUERY DOCUMENT WITH USER $USER AND LEVEL Secret${NC}"
    curl -s -g -H "Accept: application/json" -H "Content-Type: application/json" \
        -X POST "$SITE" -u "$USER:$PASSWORD" -d "@queries/resource-server/queryDocument_Secret.txt" | jq .
    echo
done

echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"