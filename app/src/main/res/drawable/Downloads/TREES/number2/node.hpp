#include <iostream>
#include <algorithm>
#include <cmath>
#include <cstdlib>
using namespace std;

struct node {
    node* parent;
    node* right;
    node* left;
    int elem;

    // TODO paste your height method here
    int height() {
        if(!this) return 0;
        return 1 + max(left->height(), right->height());
    }
};