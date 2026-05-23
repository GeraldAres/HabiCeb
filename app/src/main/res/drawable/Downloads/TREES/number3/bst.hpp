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