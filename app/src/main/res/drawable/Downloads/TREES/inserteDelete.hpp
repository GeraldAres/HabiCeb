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

#include "mybinarytree.hpp"

class BST {
	BinaryTree* tree = new MyBinaryTree();

	public:
	bool search(int num) {
		return search_node(tree->getRoot(), num);
	}

	bool search_node(node* n, int num) {
		if (n == NULL) {
			return false;
		}
		if (n->elem == num) {
			return true;
		}
		if (num > n->elem) {
			// proceed to right
			return search_node(n->right, num);
		} else {
			return search_node(n->left, num);
		}
	}

    // TODO perform post-processing by checking for violation after insertion
    // from the node inserted (or from its parent) until the root
	node* insert(int num) {
		node* n = tree->getRoot();
		if (n == NULL) {
			return tree->addRoot(num);
		}
		node* temp = insert_node(n, num);
 		int e = bal(n);
		return temp;
	}

	node* insert_node(node* n, int num) {
		if (n == NULL) {
			return NULL;
		}
		if (n->elem == num) {
			return NULL;
		}
		if (num > n->elem) {
			if (!n->right) {
				return tree->addRight(n, num);
			} else {
				return insert_node(n->right, num);
			}
		} else {
			if (!n->left) {
				return tree->addLeft(n, num);
			} else {
				return insert_node(n->left, num);
			}
		}
	}

    int bal(node* curr){
        if(!curr) return 0;
        int leftH = bal(curr->left), rightH = bal(curr->right);
        leftH = curr->left->height(), rightH = curr->right->height();
        //cout << curr->elem << endl << leftH << endl << rightH << endl << endl;
        if (leftH - rightH < -1 || leftH - rightH > 1){
            //cout << curr->left->elem << endl;
            restructure(curr);
            //update height if restructured
        } 
        leftH = curr->left->height(), rightH = curr->right->height();
        //cout << curr->elem << endl << leftH << endl << rightH << endl << endl;
        return 1 + max(leftH, rightH);
    }

    // TODO perform post-processing by checking for violation after deletion
    // from the parent of the node removed until the root
    bool remove(int num) {
        bool temp = remove_node(tree->getRoot(), num);
        node* n = tree->getRoot();
 		int e = bal(n);
		return temp;
    }

	bool remove_node(node* n, int num) {
		if (n == NULL) {
			return false;
		}
		if (n->elem == num) {
            if (n->left && n->right) {
                node* r = n->right;
                while (r->left) {
                    r = r->left;
                }
                int rem = tree->remove(r);
                n->elem = rem;
            } else {
    			tree->remove(n);
            }
            return true;
		}
		if (num > n->elem) {
			return remove_node(n->right, num);
		} else {
			return remove_node(n->left, num);
		}
	}

    // GIVEN the grandparent (or z), find the parent (or y), and the child (or x).
    bool restructure(node* gp) {
        node* par; // parent
        // TODO find parent
        int leftHeight = 0, rightHeight = 0;
        if(gp->left) leftHeight = gp->left->height();
        if(gp->right) rightHeight = gp->right->height();
        if(leftHeight > rightHeight) par = gp->left;
        else par = gp->right;
        //cout << leftHeight << endl << rightHeight << endl;
        // This is an indicator of the placement of grandparent to parent (gtop)
        bool gtop_right = false;
        if (gp->right == par) {
            gtop_right = true;
        }

        node* child;
        // TODO find child
        int cLeftHeight = 0, cRightHeight = 0;
        if(par->left) cLeftHeight = par->left->height();
        if(par->right) cRightHeight = par->right->height();
        if(cLeftHeight > cRightHeight) child = par->left;
        else child = par->right;
        //cout << child->elem << endl; 
        
        if(!gtop_right && cLeftHeight == cRightHeight) child = par->left;
        // This is an indicator of the placement of parent to child (ptoc)
        bool ptoc_right = false;
        if (par->right == child) {
            ptoc_right = true;
        }

        // FOR THE FOLLOWING: Write in each of the if statements a console output
        // on its corresponding operation (ZIGLEFT, ZIGRIGHT, ZIGZAGLEFT, or ZIGZAGRIGHT)

        // z
        //  \
        //   y
        //    \
        //     x
        //cout << gtop_right << endl << ptoc_right << endl;
        if (gtop_right && ptoc_right) {
            // TODO call to either zigleft or zigright or both
            cout << "ZIGLEFT" << endl;
            zigleft(par);
        }

        // z
        //   \
        //     y
        //    /
        //   x
        else if (gtop_right && !ptoc_right) {
            // TODO call to either zigleft or zigright or both
            cout << "ZIGZAGLEFT" << endl;
            
            //zigleft(par);
            zigright(child);
            zigleft(child);
        }

        //     z
        //    /
        //   y
        //  /
        // x
        else if (!gtop_right && !ptoc_right) {
            // TODO call to either zigleft or zigright or both
            cout << "ZIGRIGHT" << endl;
            zigright(par);
        }

        //      z
        //    /
        //  y
        //   \
        //    x
        else {
            // TODO call to either zigleft or zigright or both
            cout << "ZIGZAGRIGHT" << endl;
            zigleft(child);
            zigright(child);
        }

        return true;
    }

    void zigleft(node* curr) {
        tree->zigleft(curr);
    }

    void zigright(node* curr) {
        tree->zigright(curr);
    }

	void print() {
		tree->print();
	}
};

#include <cstdlib>
#include <iostream>
#include "binarytree.hpp"
using namespace std;

class MyBinaryTree : public BinaryTree {
	node* root;
	int size;

	node* create_node(int e, node* parent) {
		node* n = (node*) calloc( 1, sizeof(node) );
		n->elem = e;
		n->parent = parent;
		return n;
	}

	public:
	node* addRoot(int e) {
		if (root) {
			cout << "Root exists" << endl;
			return NULL;
		}
		node* n = create_node(e, NULL);
		root = n;
		size++;
		return n;
	}

	node* left(node* p) {
		return p->left;
	}

	node* right(node* p) {
		return p->right;
	}

	node* sibling(node* n) {
		node* par = n->parent;
		if (!par) {
			return NULL;
		}
		if (n == par->left) {
			return par->right;
		}
		return par->left;
	}

	node* addLeft(node* p, int e) {
		if (p->left) {
			cout << "Left of " << p->elem << " exists" << endl;
			return NULL;
		}
		node* n = create_node(e, p);
		p->left = n;
		size++;
		return n;
	}

	node* addRight(node* p, int e) {
		if (p->right) {
			cout << "Right of " << p->elem << " exists" << endl;
			return NULL;
		}
		node* n = create_node(e, p);
		p->right = n;
		size++;
		return n;
	}

    int remove(node* n) {
        int res = n->elem;
        if (left(n) && right(n)) {
            return -1;
        }
        if (!left(n) && !right(n)) {
            if (n->parent) {
                node* par = n->parent;
                if (left(par) == n) {
                    par->left = NULL;
                } else {
                    par->right = NULL;
                }
            } else {
                root = NULL;
            }
        } else {
            node* child;
            if (left(n)) {
                child = left(n);
            } else {
                child = right(n);
            }
            if (n->parent) {
                node* par = n->parent;
                if (left(par) == n) {
                    par->left = child;
                } else {
                    par->right = child;
                }
                child->parent = par;
            } else {
                root = child;
                child->parent = NULL;
            }
        }
        size--;
        free(n);
        return res;
    }

	node* getRoot() {
		return root;
	}

    // TODO implement zigleft
    // params: curr - the right child that will be rotated with its parent
    // after which, curr shall be above its parent
    //  |
    //  y
    //   \
    //    x <- curr
    void zigleft(node* curr) {
        node *temp = curr->parent;
        curr->parent = temp->parent;
        // update temp->parent->left or right
        if(temp == root) root = curr;
        else {
            if (temp == curr->parent->left){
                curr->parent->left = curr;
                //cout << curr->parent->elem << endl << curr->parent->left->elem;
            }
            else if (temp == curr->parent->right) curr->parent->right = curr;
        }
        temp->parent = curr;
        // what if temp is root
    
        temp->right = NULL;
        if(curr->left){
            temp->right = curr->left;
            curr->left->parent = temp;
        }
        curr->left = temp;
    }

    // TODO implement zigright
    // params: curr - the left child that will be rotated with its parent
    // after which, curr shall be above its parent
    //   |
    //   y
    //  /
    // x <- curr
    void zigright(node* curr) {
        node *temp = curr->parent;
        curr->parent = temp->parent;
        // update temp->parent->left or right
        if(temp == root) root = curr;
        else {
            if (temp == curr->parent->left){
                curr->parent->left = curr;
                //cout << curr->parent->elem << endl << curr->parent->left->elem;
            }
            else if (temp == curr->parent->right) curr->parent->right = curr;
        }
        temp->parent = curr;
        
        temp->left = NULL;
        if(curr->right){
            temp->left = curr->right;
            curr->right->parent = temp;
        }
        curr->right = temp;
    }

	void print() {
		cout << "Size: " << size << endl;
		if (!root) {
			cout << "EMPTY" << endl;
			return;
		}
		node* curr = root;
		print_node("", root, false);
        cout << "Status: " << check_parent(root, NULL) << endl;
	}

	void print_node(string prefix, node* n, bool isLeft) {
		cout << prefix;
        cout << (isLeft ? "+--L: " : "+--R: " );
        cout << n->elem << endl;
		if (n->left) {
			print_node(prefix + "|   ", n->left, true);
		}
		if (n->right) {
			print_node(prefix + "|   ", n->right, false);
		}
	}

    bool check_parent(node* curr, node* par) {
        if (!curr) {
            return true;
        }
        if (curr->parent != par) {
            if (!curr->parent) {
                cout << "Illegal parent of " << curr->elem << ": NULL -- must be " << par->elem << endl;
            } else if (!par) {
                cout << "Illegal parent of " << curr->elem << ": " << curr->parent->elem << "must be NULL" << endl;
            } else {
                cout << "Illegal parent of " << curr->elem << ": " << curr->parent->elem << " -- must be " << par->elem << endl;
            }
            return false;
        }
        return check_parent(curr->left, curr) && check_parent(curr->right, curr);
    }
};