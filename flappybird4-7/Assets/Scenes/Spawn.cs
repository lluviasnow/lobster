using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spawn : MonoBehaviour
{
    public GameObject colum;
    public float colddown = 2f;
    float nextspown;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (Time.time > nextspown)
        {
            nextspown = Time.time + colddown;
            Vector3 spawnp = transform.position;
            spawnp.y += Random.Range(-3.5f, 3.5f);
            Instantiate(colum,spawnp,transform.rotation);
        }

    }
}
