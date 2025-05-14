#!/usr/bin/env bash
SITE="http://company:8081/graphql"
BRIGHT_RED='\033[1;31m'
BRIGHT_GREEN='\033[1;32m'
#BRIGHT_BLUE='\033[1;34m'
BRIGHT_CYAN='\033[1;36m'
#BRIGHT_MAGENTA='\033[1;35m'
BRIGHT_YELLOW='\033[1;33m'
#BRIGHT_WHITE='\033[1;37m'
NC='\033[0m' # No Color
PASSWORD="clandestine"

echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"
echo -e "${BRIGHT_CYAN}MUTATE DEPARTMENTS${NC}"
curl -s -g -H "Accept: application/json" -H "Content-Type: application/json" \
    -X POST "$SITE" -u "$USER:$PASSWORD" -d "@queries/company/mutateDepartments.txt"
echo

echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"
echo -e "${BRIGHT_CYAN}QUERY ALL DEPARTMENTS${NC}"
curl -s -g -H "Accept: application/json" -H "Content-Type: application/json" \
    -X POST "$SITE" -u "$USER:$PASSWORD" -d "@queries/company/queryDepartments.txt" | jq .
echo

echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"
echo -e "${BRIGHT_CYAN}QUERY DEPARTMENT BY ID${NC}"
curl -s -g -H "Accept: application/json" -H "Content-Type: application/json" \
    -X POST "$SITE" -u "$USER:$PASSWORD" -d "@queries/company/queryDepartmentById.txt" | jq .
echo

echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"
echo -e "${BRIGHT_CYAN}QUERY EMPLOYEES COUNT BY TITLE${NC}"
curl -s -g -H "Accept: application/json" -H "Content-Type: application/json" \
    -X POST "$SITE" -u "$USER:$PASSWORD" -d "@queries/company/queryEmployeesCountByTitle.txt" | jq .
echo

echo -e "${BRIGHT_YELLOW}----------------------------------------------------------------------${NC}"