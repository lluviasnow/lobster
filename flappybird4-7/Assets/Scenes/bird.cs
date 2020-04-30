using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class bird : MonoBehaviour
{
    Rigidbody2D rg;

    public float upForce = 200f;
    // Start is called before the first frame update
    void Start()
    {
        rg = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown("space")&&GM.isActive)
        {
            rg.AddForce(Vector2.up * upForce);
        }
    }
}
